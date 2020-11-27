package com.linzeming.proxypoolweb.dao.impl;

import com.linzeming.proxypoolweb.dao.ProxyIpDao;
import com.linzeming.proxypoolweb.model.ProxyIp;
import com.linzeming.proxypoolweb.util.Constants;
import com.linzeming.proxypoolweb.util.Converter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Tuple;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component(value = "proxyIpDao")
public class ProxyIpDaoRedisImpl implements ProxyIpDao {
    @Autowired
    JedisPool jedisPool;

    @Override
    public List<ProxyIp> getAllProxyByScoreRange(Double scoreMin, Double scoreMax) {
        List<ProxyIp> rtnList = new ArrayList<>();
        try (Jedis resource = jedisPool.getResource()) {
            Set<Tuple> tuples = resource.zrangeByScoreWithScores(Constants.redisValidatedProxiesZset, scoreMin, scoreMax);
            for (Tuple tuple : tuples) {
                ProxyIp proxyIp = Converter.convertProxy(tuple.getElement());
                proxyIp.setScore(tuple.getScore());
                if (tuple.getScore() < Constants.validatedProxyMaxScore) {
                    proxyIp.setLastValidateResult(false);
                } else {
                    proxyIp.setLastValidateResult(true);
                }
                Long proxyLastValidateTimestamp = getProxyLastValidateTimestamp(proxyIp);
                proxyIp.setValidateTimestamp(proxyLastValidateTimestamp);
                rtnList.add(proxyIp);
            }
        }
        return rtnList;
    }

    @Override
    public Long countProxyByScoreRange(Double scoreMin, Double scoreMax) {
        Long zcount = 0L;
        try (Jedis resource = jedisPool.getResource()) {
            zcount = resource.zcount(Constants.redisValidatedProxiesZset, scoreMin, scoreMax);
        }
        return zcount;
    }

    @Override
    public Long getProxyLastValidateTimestamp(ProxyIp proxyIp) {
        if (proxyIp == null) {
            return null;
        }
        try (Jedis resource = jedisPool.getResource()) {
            String hget = resource.hget(Constants.redisProxiesValidateLastTimeHashes, proxyIp.formattedIpPort());
            return Long.parseLong(hget);
        }
    }
}
