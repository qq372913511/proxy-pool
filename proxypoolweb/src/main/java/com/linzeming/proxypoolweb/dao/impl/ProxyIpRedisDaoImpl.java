package com.linzeming.proxypoolweb.dao.impl;

import com.linzeming.proxypoolweb.dao.ProxyIpRedisDao;
import com.linzeming.proxypoolweb.model.ProxyIpValidateLogResult;
import com.linzeming.proxypoolweb.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Component(value = "proxyIpRedisDao")
public class ProxyIpRedisDaoImpl implements ProxyIpRedisDao {

    @Autowired
    JedisPool jedisPool;


    @Override
    public List<ProxyIpValidateLogResult> selectProxyIpValidateResultByIpAndPort(String ip, Integer port) {
        try (Jedis resource = jedisPool.getResource();) {
            Map<String, String> stringStringMap = resource.hgetAll(Constants.validateLogKeyPrefix + ":" + ip + ":" + port);
            ArrayList<ProxyIpValidateLogResult> proxyIpValidateLogResults = new ArrayList<>(stringStringMap.size());
            for (int i = 0; i < stringStringMap.size(); i++) {
                proxyIpValidateLogResults.add(new ProxyIpValidateLogResult(ip + port));
            }

            int i1 = 0;
            for (String key: stringStringMap.keySet()){
                proxyIpValidateLogResults.get(i1).setLocalDateTime(key);
                i1++;
            }
            int i2 = 0;
            for (String value: stringStringMap.values()){
                proxyIpValidateLogResults.get(i2).setConnctionSpeed(value);
                i2++;
            }
            return proxyIpValidateLogResults;
        }
    }
}
