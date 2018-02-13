package com.sevendegree.common;

import com.sevendegree.util.PropertiesUtil;
import lombok.extern.slf4j.Slf4j;
import org.redisson.Redisson;
import org.redisson.config.Config;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Slf4j
@Component
public class RedissonManager {

    private Config config = new Config();

    private Redisson redisson = null;

    public Redisson getRedisson() {
        return redisson;
    }

    private static String redisIp = PropertiesUtil.getProperty("redis.ip");
    private static Integer redisPort = Integer.parseInt(PropertiesUtil.getProperty("redis.port"));
    private static String redisIp2 = PropertiesUtil.getProperty("redis.ip2");

    private static Integer redisPort2 = Integer.parseInt(PropertiesUtil.getProperty("redis.port2"));

    @PostConstruct
    private void init() {

        try {
            config.useSingleServer().setAddress(new StringBuilder().append(redisIp).append(":").append(redisPort).toString());
            redisson = (Redisson) Redisson.create(config);
            log.info("初始化Redisson结束");
        } catch (Exception e) {
            log.error("Redisson init error", e);
        }
    }
}
