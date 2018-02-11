package com.sevendegree.task;

import com.sevendegree.service.IOrderService;
import com.sevendegree.util.PropertiesUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class CloseOrderTask {

    @Autowired
    private IOrderService iOrderService;

    @Scheduled(cron = "0 */1 * * * ?")//每个一分钟的整数倍
    public void closeOrderTaskV1() {
        log.info("关闭订单定时任务开始");
        int hour = Integer.parseInt(PropertiesUtil.getProperty("close.order.task.hour", "2"));
//        iOrderService.closeOrder(hour);
        log.info("关闭订单定时任务结束");
    }

}
