package com.linzeming.proxypool.crawler.dao.impl;

import com.linzeming.proxypool.crawler.dao.ProxyIpValidateLogResultMongodbDao;
import com.linzeming.proxypool.crawler.model.ProxyIpValidateLogResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

@Component(value = "proxyIpValidateLogResultMongodbDao")
public class ProxyIpValidateLogResultMongodbDaoImpl implements ProxyIpValidateLogResultMongodbDao {
    @Autowired
    MongoTemplate mongoTemplate;


    @Override
    public void insertProxyIpValidateLogResult(ProxyIpValidateLogResult proxyIpValidateLogResult) {
        mongoTemplate.insert(proxyIpValidateLogResult);
    }
}
