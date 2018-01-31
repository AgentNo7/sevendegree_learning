package com.sevendegree.common;

import com.google.common.collect.Lists;
import com.sevendegree.util.PropertiesUtil;
import redis.clients.jedis.*;
import redis.clients.util.Hashing;

import java.util.ArrayList;
import java.util.List;

public class RedisShardedPool {
    private static ShardedJedisPool pool; //sharded Jedis连接池

    private static Integer maxTotal = Integer.parseInt(PropertiesUtil.getProperty("redis.max.total", "20")); //最大连接数

    private static Integer maxIdle = Integer.parseInt(PropertiesUtil.getProperty("redis.max.idle", "10")); //连接池最大空闲jedis实例数

    private static Integer minIdle = Integer.parseInt(PropertiesUtil.getProperty("redis.min.idle", "2")); //连接池最小空闲jedis实例数

    private static Boolean testOnBorrow = Boolean.parseBoolean(PropertiesUtil.getProperty("redis.test.borrow", "true")); //在borrow一个jedis实例的时候，是否要进行验证操作，如果赋值为true，则得到的jedis实例肯定是可用的

    private static Boolean testOnReturn = Boolean.parseBoolean(PropertiesUtil.getProperty("redis.test.return", "true")); //在return一个jedis实例的时候，是否要进行验证操作，如果赋值为true，则放回jedisPool的jedis实例肯定是可用的

    private static String redisIp = PropertiesUtil.getProperty("redis.ip");

    private static Integer redisPort = Integer.parseInt(PropertiesUtil.getProperty("redis.port"));

    private static String redisIp2 = PropertiesUtil.getProperty("redis.ip2");

    private static Integer redisPort2 = Integer.parseInt(PropertiesUtil.getProperty("redis.port2"));


    private static void initPool() {
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(maxTotal);
        config.setMaxIdle(maxIdle);
        config.setMinIdle(minIdle);
        config.setTestOnBorrow(testOnBorrow);
        config.setTestOnReturn(testOnReturn);

        config.setBlockWhenExhausted(true); //连接耗尽是是否阻塞，false会抛出异常，true阻塞直到超时。默认为true

        JedisShardInfo info = new JedisShardInfo(redisIp, redisPort, 60 * 2);
//        info.setPassword(); if you seted password
        JedisShardInfo info2 = new JedisShardInfo(redisIp2, redisPort2, 60 * 2);

        List<JedisShardInfo> jedisShardInfos = new ArrayList<>(2);

        jedisShardInfos.add(info);
        jedisShardInfos.add(info2);

        pool = new ShardedJedisPool(config, jedisShardInfos, Hashing.MURMUR_HASH); //MURMUR_HASH corresponding to CONSISTENT HASH
    }

    static {
        initPool();
    }

    public static ShardedJedis getJedis() {
        return pool.getResource();
    }

    public static void returnResource(ShardedJedis jedis) {
        pool.returnResource(jedis);
    }

    public static void returnBrokenResource(ShardedJedis jedis) {
        pool.returnResource(jedis);
    }

//    public static void main(String[] args) {
//        ShardedJedis jedis = pool.getResource();
//
//        for (int i=0;i<10;i++) {
//            jedis.set("key" + i, "value" + i);
//        }
////        pool.returnResource(jedis);
//        // jedis.set("test", "TwiceTest");
//        returnResource(jedis);
////        pool.destroy();//临时调用
//        System.out.println("end");
//    }
}
