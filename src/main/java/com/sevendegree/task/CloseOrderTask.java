package com.sevendegree.task;

import com.google.zxing.oned.rss.RSSUtils;
import com.sevendegree.common.Const;
import com.sevendegree.service.IOrderService;
import com.sevendegree.util.PropertiesUtil;
import com.sevendegree.util.RedisShardedUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class CloseOrderTask {

    @Autowired
    private IOrderService iOrderService;

//    @Scheduled(cron = "0 */1 * * * ?")//每个一分钟的整数倍
    public void closeOrderTaskV1() {
        log.info("关闭订单定时任务开始");
        int hour = Integer.parseInt(PropertiesUtil.getProperty("close.order.task.hour", "2"));
//        iOrderService.closeOrder(hour);
        log.info("关闭订单定时任务结束");
    }

    @Scheduled(cron = "0 */1 * * * ?")//每个一分钟的整数倍
    public void closeOrderTaskV2() {
        log.info("关闭订单定时任务开始");
        long lockTimeOut = Long.parseLong(PropertiesUtil.getProperty("lock.timeout", "5000"));

        Long setnxResult = RedisShardedUtil.setnx(Const.REDIS_LOCK.CLOSE_ORDER_TASK_LOCK, String.valueOf(System.currentTimeMillis() + lockTimeOut));

        if (setnxResult != null && setnxResult == 1) {
            //返回值是以表示获取所成功（不存在key设置该key成功
            closeOrderAndExpireLock();
        } else{
            log.info("没有获得分布式锁：{}", Const.REDIS_LOCK.CLOSE_ORDER_TASK_LOCK );
        }

//        iOrderService.closeOrder(hour);
        log.info("关闭订单定时任务结束");
    }

    private void closeOrderAndExpireLock() {
        RedisShardedUtil.expire(Const.REDIS_LOCK.CLOSE_ORDER_TASK_LOCK, 50);//有效期50秒防止死锁
        log.info("获取{}ThreadName:{}", Const.REDIS_LOCK.CLOSE_ORDER_TASK_LOCK, Thread.currentThread().getName());
        int hour = Integer.parseInt(PropertiesUtil.getProperty("close.order.task.hour", "2"));
        iOrderService.closeOrder(hour);
        RedisShardedUtil.del(Const.REDIS_LOCK.CLOSE_ORDER_TASK_LOCK);
        log.info("释放{}ThreadName:{}", Const.REDIS_LOCK.CLOSE_ORDER_TASK_LOCK, Thread.currentThread().getName());
        log.info("========================================");
    }
}
