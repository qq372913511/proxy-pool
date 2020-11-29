package com.linzeming.proxypool.crawler.dao.impl;

import com.linzeming.proxypool.crawler.dao.ProxyIpDao;
import com.linzeming.proxypool.crawler.model.ProxyIp;
import com.linzeming.proxypool.crawler.util.Constants;
import com.linzeming.proxypool.crawler.util.ProxyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Pipeline;
import redis.clients.jedis.params.ZAddParams;

import java.util.List;
import java.util.Set;


@Component(value = "proxyIpDao")
public class ProxyIpDaoRedisImpl implements ProxyIpDao {
    @Autowired
    JedisPool jedisPool;

    @Override
    public void addIntoValidateProxiesQueue(ProxyIp proxyIp) {
        Jedis resource = jedisPool.getResource();
        resource.sadd(Constants.redisValidateProxiesQueueSet, proxyIp.getFormattedIpPort());
        resource.close();
    }

    @Override
    public void addIntoValidateProxiesQueueMany(List<ProxyIp> proxyIps) {
        Jedis resource = jedisPool.getResource();
        Pipeline p = resource.pipelined();
        for (ProxyIp proxyIp: proxyIps){
            resource.sadd(Constants.redisValidateProxiesQueueSet, proxyIp.getFormattedIpPort());
        }
        p.syncAndReturnAll();
        resource.close();
    }

    @Override
    public ProxyIp takeFromValidateProxiesQueue() {
        Jedis resource = jedisPool.getResource();
        String spop = resource.spop(Constants.redisValidateProxiesQueueSet);
        ProxyIp proxyIp = ProxyUtils.convertProxy(spop);
        resource.close();
        return proxyIp;
    }

    @Override
    public void addIntoProxiesZsetNx(ProxyIp proxyIp) {
        Jedis resource = jedisPool.getResource();
        ZAddParams zAddParams = new ZAddParams();
        zAddParams.nx();
        resource.zadd(Constants.redisValidatedProxiesZset, proxyIp.getScore(), proxyIp.getFormattedIpPort(), zAddParams);
        resource.close();
    }


    @Override
    public boolean existsInValidateProxiesQueue(ProxyIp proxyIp) {
        Jedis resource = jedisPool.getResource();
        Boolean sismember = resource.sismember(Constants.redisValidateProxiesQueueSet, proxyIp.getFormattedIpPort());
        resource.close();
        return sismember;
    }

    @Override
    public boolean existsInProxiesZset(ProxyIp proxyIp) {
        Jedis resource = jedisPool.getResource();
        Long zrank = resource.zrank(Constants.redisValidatedProxiesZset, proxyIp.getFormattedIpPort());
        resource.close();
        return zrank != null;
    }

    @Override
    public void setLastValidateTime(ProxyIp proxyIp) {
        Jedis resource = jedisPool.getResource();
        resource.hset(Constants.redisProxiesValidateLastTimeHashes, proxyIp.getFormattedIpPort(), String.valueOf(proxyIp.getValidateTimestamp()));
        resource.close();
    }

    @Override
    public long getLastValidateTime(ProxyIp proxyIp) {
        Jedis resource = jedisPool.getResource();
        String hget = resource.hget(Constants.redisProxiesValidateLastTimeHashes, proxyIp.getFormattedIpPort());
        resource.close();
        if (hget == null) {
            return 0L;
        } else {
            return Long.parseLong(hget);
        }
    }

    @Override
    public long countValidateProxiesQueue() {
        Jedis resource = jedisPool.getResource();
        Long scard = resource.scard(Constants.redisValidateProxiesQueueSet);
        resource.close();
        return scard;
    }

    @Override
    public Set<String> getRangeProxiesByScore(Double minScore, Double maxScore) {
        Jedis resource = jedisPool.getResource();
        Set<String> strings = resource.zrangeByScore(Constants.redisValidatedProxiesZset, minScore, maxScore);
        resource.close();
        return strings;
    }

    @Override
    public double getProxyScore(ProxyIp proxyip) {
        Jedis resource = jedisPool.getResource();
        Double score = resource.zscore(Constants.redisValidatedProxiesZset, proxyip.getFormattedIpPort());
        resource.close();
        return score;
    }

    @Override
    public void decreaseProxyScore(ProxyIp proxyIp, double deltaScore) {
        Jedis resource = jedisPool.getResource();
        resource.zincrby(Constants.redisValidatedProxiesZset, -1 * deltaScore, proxyIp.getFormattedIpPort());
        resource.close();
    }

    @Override
    public void increaseProxyScore(ProxyIp proxyIp, double deltaScore) {
        Jedis resource = jedisPool.getResource();
        resource.zincrby(Constants.redisValidatedProxiesZset, deltaScore, proxyIp.getFormattedIpPort());
        resource.close();
    }

    @Override
    public void setProxyScore(ProxyIp proxyIp) {
        Jedis resource = jedisPool.getResource();
        resource.zadd(Constants.redisValidatedProxiesZset, proxyIp.getScore(), proxyIp.getFormattedIpPort());
        resource.close();
    }

    @Override
    public void addValidatePorxyResult(ProxyIp proxyIp) {
        Jedis resource = jedisPool.getResource();
        resource.lpush(Constants.redisProxiesValidateAllTimeList, proxyIp.getFormattedIpValidateTimestampResult());
        resource.close();
    }
}
