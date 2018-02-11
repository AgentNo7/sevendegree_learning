package com.sevendegree.util;

import com.sevendegree.common.RedisShardedPool;
import lombok.extern.slf4j.Slf4j;
import redis.clients.jedis.ShardedJedis;

@Slf4j
public class RedisShardedUtil {
    //exTime 单位是秒
    public static Long expire(String key, int exTime) {
        ShardedJedis jedis = null;
        Long result = null;

        try {
            jedis = RedisShardedPool.getJedis();
            result = jedis.expire(key, exTime);
        } catch (Exception e) {
            log.error("expire key: {} error", key, e);
            RedisShardedPool.returnBrokenResource(jedis);
            e.printStackTrace();
            return result;
        }
        RedisShardedPool.returnResource(jedis);
        return result;
    }

    //exTime 单位是秒
    public static String setEx(String key, String value, int exTime) {
        ShardedJedis jedis = null;
        String result = null;

        try {
            jedis = RedisShardedPool.getJedis();
            result = jedis.setex(key, exTime, value);
        } catch (Exception e) {
            log.error("setex key: {} value: {} error", key, value, e);
            RedisShardedPool.returnBrokenResource(jedis);
            e.printStackTrace();
            return result;
        }
        RedisShardedPool.returnResource(jedis);
        return result;
    }

    public static String set(String key, String value) {
        ShardedJedis jedis = null;
        String result = null;

        try {
            jedis = RedisShardedPool.getJedis();
            result = jedis.set(key, value);
        } catch (Exception e) {
            log.error("set key: {} value: {} error", key, value, e);
            RedisShardedPool.returnBrokenResource(jedis);
            e.printStackTrace();
            return result;
        }
        RedisShardedPool.returnResource(jedis);
        return result;
    }

    public static String get(String key) {
        ShardedJedis jedis = null;
        String result = null;

        try {
            jedis = RedisShardedPool.getJedis();
            result = jedis.get(key);
        } catch (Exception e) {
            log.error("get key: {} error", key, e);
            RedisShardedPool.returnBrokenResource(jedis);
            e.printStackTrace();
            return result;
        }
        RedisShardedPool.returnResource(jedis);
        return result;
    }

    public static Long del(String key) {
        ShardedJedis jedis = null;
        Long result = null;

        try {
            jedis = RedisShardedPool.getJedis();
            result = jedis.del(key);
        } catch (Exception e) {
            log.error("delete key: {} error", key, e);
            RedisShardedPool.returnBrokenResource(jedis);
            e.printStackTrace();
            return result;
        }
        RedisShardedPool.returnResource(jedis);
        return result;
    }

    public static Long setnx(String key, String value) {
        ShardedJedis jedis = null;
        Long result = null;

        try {
            jedis = RedisShardedPool.getJedis();
            result = jedis.setnx(key, value);
        } catch (Exception e) {
            log.error("set key: {} value: {} error", key, value, e);
            RedisShardedPool.returnBrokenResource(jedis);
            e.printStackTrace();
            return result;
        }
        RedisShardedPool.returnResource(jedis);
        return result;
    }

}
