package com.linzeming.proxypool.crawler;

import com.linzeming.proxypool.crawler.util.Constants;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.params.ZAddParams;

import java.util.Map;

public class addSomeProxy {

    public static void main(String[] args) {
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        //控制一个pool可分配多少个jedis实例
        jedisPoolConfig.setMaxTotal(1000);
        //控制一个pool最多有多少个状态为idle(空闲)的jedis实例
        jedisPoolConfig.setMaxIdle(200);
        //表示当borrow一个jedis实例时，最大的等待时间，如果超过等待时间，则直接抛出JedisConnectionException
        jedisPoolConfig.setMaxWaitMillis(2000);
        //在borrow一个jedis实例时，是否提前进行validate操作；如果为true，则得到的jedis实例均是可用的
        jedisPoolConfig.setTestOnBorrow(true);
        JedisPool jedisPool = new JedisPool(jedisPoolConfig, "10.0.0.88", 6379);

        Jedis resource = jedisPool.getResource();

        Map<String, String> stringStringMap = resource.hgetAll("proxypool:proxiesValidateLastTimeHashes");
        System.out.println(stringStringMap.size());
        ZAddParams zAddParams = new ZAddParams();
        zAddParams.nx();
        for (String key : stringStringMap.keySet()) {
            resource.zadd(Constants.redisValidatedProxiesZset, 5, key, zAddParams);
            System.out.println(key);
        }
        resource.close();
    }

}
