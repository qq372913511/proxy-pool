package com.linzeming.proxypool.crawler.dao.impl;

import com.alibaba.fastjson.JSON;
import com.linzeming.proxypool.crawler.dao.ProxyIpRedisDao;
import com.linzeming.proxypool.crawler.model.ProxyIp;
import com.linzeming.proxypool.crawler.model.ProxyIpValidateLogResult;
import com.linzeming.proxypool.crawler.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.List;
import java.util.Set;


@Component(value = "proxyIpRedisDao")
public class ProxyIpDaoRedisImpl implements ProxyIpRedisDao {
    @Autowired
    JedisPool jedisPool;


    @Override
    public void insertProxyIpIntoValidateQueue(List<ProxyIp> proxyIpList) {
        if (proxyIpList == null) {
            return;
        }
        try (Jedis resource = jedisPool.getResource();) {
            String[] pushStrings = new String[proxyIpList.size()];
            for (int i = 0; i < proxyIpList.size(); i++) {
                pushStrings[i] = JSON.toJSONString(proxyIpList.get(i));
            }
            resource.lpush(Constants.redisValidateProxiesQueueList, pushStrings);
        }
    }

    @Override
    public ProxyIp takeProxyFromValidateQueue() {
        try (Jedis resource = jedisPool.getResource();) {
            String rpop = resource.rpop(Constants.redisValidateProxiesQueueList);
            return JSON.parseObject(rpop, ProxyIp.class);
        }
    }


//    @Override
//    public void logValidateResult(ProxyIpValidateLogResult proxyIpValidateLogResult) {
//        try (Jedis resource = jedisPool.getResource();) {
//            resource.hset(Constants.validateLogKeyPrefix + ":" + proxyIpValidateLogResult.getIpPort(),
//                    proxyIpValidateLogResult.getLocalDateTime(),proxyIpValidateLogResult.getConnctionSpeed());
//        }
//    }
}
