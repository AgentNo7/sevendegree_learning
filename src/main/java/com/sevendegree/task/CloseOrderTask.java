package com.sevendegree.task;

import com.sevendegree.common.Const;
import com.sevendegree.common.RedissonManager;
import com.sevendegree.service.IOrderService;
import com.sevendegree.util.PropertiesUtil;
import com.sevendegree.util.RedisShardedUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.redisson.api.RLock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;
import java.util.concurrent.TimeUnit;

@Component
@Slf4j
public class CloseOrderTask {

    @Autowired
    private IOrderService iOrderService;

    @Autowired
    private RedissonManager redissonManager;

    @PreDestroy
    public void delLock() {
        RedisShardedUtil.del(Const.REDIS_LOCK.CLOSE_ORDER_TASK_LOCK);
    }

//    @Scheduled(cron = "0 */1 * * * ?")//每个一分钟的整数倍
    public void closeOrderTaskV1() {
        log.info("关闭订单定时任务开始");
        int hour = Integer.parseInt(PropertiesUtil.getProperty("close.order.task.hour", "2"));
//        iOrderService.closeOrder(hour);
        log.info("关闭订单定时任务结束");
    }

//    @Scheduled(cron = "0 */1 * * * ?")//每个一分钟的整数倍
    public void closeOrderTaskV2() {
        log.info("关闭订单定时任务开始");
        long lockTimeOut = Long.parseLong(PropertiesUtil.getProperty("lock.timeout", "5000"));
        Long setnxResult = RedisShardedUtil.setnx(Const.REDIS_LOCK.CLOSE_ORDER_TASK_LOCK, String.valueOf(System.currentTimeMillis() + lockTimeOut));
        if (setnxResult != null && setnxResult.intValue() == 1) {
            //返回值是以表示获取所成功（不存在key设置该key成功
            closeOrderAndExpireLock();
        } else{
            log.info("没有获得分布式锁：{}", Const.REDIS_LOCK.CLOSE_ORDER_TASK_LOCK );
        }
        log.info("关闭订单定时任务结束");
    }

//    @Scheduled(cron = "0 */1 * * * ?")//每个一分钟的整数倍
    public void closeOrderTaskV3() {
        log.info("关闭订单定时任务开始");
        long lockTimeOut = Long.parseLong(PropertiesUtil.getProperty("lock.timeout", "5000"));
        Long setnxResult = RedisShardedUtil.setnx(Const.REDIS_LOCK.CLOSE_ORDER_TASK_LOCK, String.valueOf(System.currentTimeMillis() + lockTimeOut));
        if (setnxResult != null && setnxResult.intValue() == 1) {
            //返回值是以表示获取所成功（不存在key设置该key成功
            closeOrderAndExpireLock();
        } else{
            String settedTimeout = RedisShardedUtil.get(Const.REDIS_LOCK.CLOSE_ORDER_TASK_LOCK);
            if (settedTimeout != null && System.currentTimeMillis() > Long.parseLong(settedTimeout)) {
                //进条件表示有权利修改锁
                String getSetResult = RedisShardedUtil.getSet(Const.REDIS_LOCK.CLOSE_ORDER_TASK_LOCK, String.valueOf(System.currentTimeMillis() + lockTimeOut));
                //再次用当前时间戳getset，修改锁并返回旧值
                //返回给定的key的旧值，进行判断是否可以获取锁  当key没有旧值时，返回nil（null），获取锁
                //因为是集群环境，get到的旧值可能已经被其他tomcat修改，所以要与前面取得的值判断一下，如果一样表示没有被其他进程获取到
                if (getSetResult == null || (getSetResult != null && StringUtils.equals(settedTimeout, getSetResult))) {
                    //被修改后为null（或者到期？）以及没被修改的话就认为真正获得锁，开始业务
                    closeOrderAndExpireLock();
                } else {
                    //锁存在且先被其他进程修改，获取锁失败
                    log.info("没有获取到分布式锁：{}", Const.REDIS_LOCK.CLOSE_ORDER_TASK_LOCK);
                }
            } else {
                //在锁的expire时间内
                log.info("没有获取到分布式锁：{}", Const.REDIS_LOCK.CLOSE_ORDER_TASK_LOCK);
            }
        }
        log.info("关闭订单定时任务结束");
    }

    @Scheduled(cron = "0 */1 * * * ?")
    public void closeOrderTaskV4() {
        RLock lock = redissonManager.getRedisson().getLock(Const.REDIS_LOCK.CLOSE_ORDER_TASK_LOCK);
        boolean getlock = false;
        try {
            getlock = lock.tryLock(2, 5, TimeUnit.SECONDS);
            if (getlock) {
                log.info("Redisson获取到分布式锁：{}，ThreadName：{}", Const.REDIS_LOCK.CLOSE_ORDER_TASK_LOCK, Thread.currentThread().getName());
                int hour = Integer.parseInt(PropertiesUtil.getProperty("close.order.task.hour", "2"));
//                iOrderService.closeOrder(hour);
            } else {
                log.info("Redisson没有获取到分布式锁：{}，ThreadName：{}", Const.REDIS_LOCK.CLOSE_ORDER_TASK_LOCK, Thread.currentThread().getName());
            }
        } catch (InterruptedException e) {
            log.error("redisson分布式锁获取异常",e);
        } finally {
            if (!getlock) {
                return;
            }
            lock.unlock();
            log.info("Reidsson分布式锁释放锁");
        }
    }

    private void closeOrderAndExpireLock() {
        RedisShardedUtil.expire(Const.REDIS_LOCK.CLOSE_ORDER_TASK_LOCK, 5);//有效期5秒防止死锁
        log.info("获取{}ThreadName:{}", Const.REDIS_LOCK.CLOSE_ORDER_TASK_LOCK, Thread.currentThread().getName());
        int hour = Integer.parseInt(PropertiesUtil.getProperty("close.order.task.hour", "2"));
//        iOrderService.closeOrder(hour);
        RedisShardedUtil.del(Const.REDIS_LOCK.CLOSE_ORDER_TASK_LOCK);
        log.info("释放{}ThreadName:{}", Const.REDIS_LOCK.CLOSE_ORDER_TASK_LOCK, Thread.currentThread().getName());
        log.info("========================================");
    }

}
