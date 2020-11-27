package com.linzeming.proxypool.crawler.dao.impl;

import com.linzeming.proxypool.crawler.dao.ProxyIpDao;
import com.linzeming.proxypool.crawler.entity.ProxyIp;
import com.linzeming.proxypool.crawler.util.Constants;
import com.linzeming.proxypool.crawler.util.ProxyUtils;
import com.sun.corba.se.impl.orbutil.closure.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.Set;


@Component(value = "proxyIpDao")
public class ProxyIpDaoRedisImpl implements ProxyIpDao {
    @Autowired
    JedisPool jedisPool;

    @Override
    public void addIntoAnonymityCheckQueue(ProxyIp proxyIp) {
        Jedis resource = jedisPool.getResource();
        resource.sadd(Constants.redisAnonymityCheckQueueSet, proxyIp.getFormattedIpPort());
        resource.close();
    }

    @Override
    public void addIntoValidateProxiesQueue(ProxyIp proxyIp) {
        Jedis resource = jedisPool.getResource();
        resource.sadd(Constants.redisValidateProxiesQueueSet, proxyIp.getFormattedIpPort());
        resource.close();
    }

    @Override
    public void addIntoBlackSet(ProxyIp proxyIp) {
        Jedis resource = jedisPool.getResource();
        resource.sadd(Constants.redisProxiesBlackSet, proxyIp.getFormattedIpPort());
        resource.close();
    }

    @Override
    public ProxyIp takeFromAnonymityCheckQueue() {
        Jedis resource = jedisPool.getResource();
        String spop = resource.spop(Constants.redisAnonymityCheckQueueSet);
        ProxyIp proxyIp = ProxyUtils.convertProxy(spop);
        resource.close();
        return proxyIp;
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
    public void addIntoValidatedProxies(ProxyIp proxyIp) {
        Jedis resource = jedisPool.getResource();
        resource.zadd(Constants.redisValidatedProxiesZset, proxyIp.getScore(), proxyIp.getFormattedIpPort());
        resource.close();
    }

    @Override
    public boolean existsInAnonymityCheckQueue(ProxyIp proxyIp) {
        Jedis resource = jedisPool.getResource();
        Boolean sismember = resource.sismember(Constants.redisAnonymityCheckQueueSet, proxyIp.getFormattedIpPort());
        resource.close();
        return sismember;
    }

    @Override
    public boolean existsInValidateProxiesQueue(ProxyIp proxyIp) {
        Jedis resource = jedisPool.getResource();
        Boolean sismember = resource.sismember(Constants.redisValidateProxiesQueueSet, proxyIp.getFormattedIpPort());
        resource.close();
        return sismember;
    }

    @Override
    public boolean existsInValidatedProxies(ProxyIp proxyIp) {
        Jedis resource = jedisPool.getResource();
        Long zrank = resource.zrank(Constants.redisValidatedProxiesZset, proxyIp.getFormattedIpPort());
        resource.close();
        return zrank != null;
    }

    @Override
    public boolean existsInBlackSet(ProxyIp proxyIp) {
        Jedis resource = jedisPool.getResource();
        Boolean sismember = resource.sismember(Constants.redisProxiesBlackSet, proxyIp.getFormattedIpPort());
        resource.close();
        return sismember;
    }

    @Override
    public void setValidateTime(ProxyIp proxyIp, long validateTime) {
        Jedis resource = jedisPool.getResource();
        resource.hset(Constants.redisProxiesValidateTimeHashes, proxyIp.getFormattedIpPort(), String.valueOf(validateTime));
        resource.close();
    }

    @Override
    public long getValidateTime(ProxyIp proxyIp) {
        Jedis resource = jedisPool.getResource();
        String hget = resource.hget(Constants.redisProxiesValidateTimeHashes, proxyIp.getFormattedIpPort());
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
    public Set<String> getRangeValidatedProxiesByScore(Double minScore, Double maxScore) {
        Jedis resource = jedisPool.getResource();
        Set<String> strings = resource.zrangeByScore(Constants.redisValidatedProxiesZset, minScore, maxScore);
        resource.close();
        return strings;
    }

    @Override
    public double getValidatedProxyScore(ProxyIp proxyip) {
        Jedis resource = jedisPool.getResource();
        Double score = resource.zscore(Constants.redisValidatedProxiesZset, proxyip.getFormattedIpPort());
        resource.close();
        return score;
    }

    @Override
    public void decreaseValidatedProxyScore(ProxyIp proxyIp, double deltaScore) {
        Jedis resource = jedisPool.getResource();
        resource.zincrby(Constants.redisValidatedProxiesZset, -1 * deltaScore, proxyIp.getFormattedIpPort());
        resource.close();
    }

    @Override
    public void increaseValidatedProxyScore(ProxyIp proxyIp, double deltaScore) {
        Jedis resource = jedisPool.getResource();
        resource.zincrby(Constants.redisValidatedProxiesZset, deltaScore, proxyIp.getFormattedIpPort());
        resource.close();
    }

    @Override
    public void setValidatedProxyScore(ProxyIp proxyIp, double targerScore) {
        Jedis resource = jedisPool.getResource();
        resource.zadd(Constants.redisValidatedProxiesZset, targerScore, proxyIp.getFormattedIpPort());
        resource.close();
    }
}
