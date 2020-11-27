package com.linzeming.proxypool.crawler.service;

import com.linzeming.proxypool.crawler.entity.ProxyIp;

public interface ProxyIpService {
    void addIntoAnonymityCheckQueue(ProxyIp proxyIp);

    void addIntoValidateProxiesQueue(ProxyIp proxyIp);

    ProxyIp takeFromAnonymityCheckQueue();

    ProxyIp takeFromValidateProxiesQueue();

    void addIntoValidatedProxies(ProxyIp proxyIp);

    boolean existsInAnonymityCheckQueue(ProxyIp proxyIp);

    boolean existsInValidateProxiesQueue(ProxyIp proxyIp);

    boolean existsInValidatedProxies(ProxyIp proxyIp);


}
