package com.linzeming.proxypool.crawler.dao;

import com.linzeming.proxypool.crawler.model.ProxyIp;
import com.linzeming.proxypool.crawler.model.ProxyIpValidateLogResult;

import java.util.List;
import java.util.Set;

public interface ProxyIpRedisDao {
    void insertProxyIpIntoValidateQueue(List<ProxyIp> proxyIpList);

    ProxyIp takeProxyFromValidateQueue();

//    void logValidateResult(ProxyIpValidateLogResult proxyIpValidateLogResult);




}
