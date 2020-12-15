package com.linzeming.proxypool.crawler.dao;

import com.linzeming.proxypool.crawler.model.ProxyIpValidateLogResult;

public interface ProxyIpValidateLogResultMongodbDao {
    void insertProxyIpValidateLogResult(ProxyIpValidateLogResult proxyIpValidateLogResult);
}
