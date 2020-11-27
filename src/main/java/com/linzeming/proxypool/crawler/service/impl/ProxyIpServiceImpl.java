package com.linzeming.proxypool.crawler.service.impl;

import com.linzeming.proxypool.crawler.dao.ProxyIpDao;
import com.linzeming.proxypool.crawler.entity.ProxyIp;
import com.linzeming.proxypool.crawler.service.ProxyIpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service("proxyService")
public class ProxyIpServiceImpl implements ProxyIpService {
    @Autowired
    ProxyIpDao proxyIpDao;

    @Override
    public void addIntoAnonymityCheckQueue(ProxyIp proxyIp) {
        proxyIpDao.addIntoAnonymityCheckQueue(proxyIp);
        System.out.println("ProxyServiceImpl addIntoAnonymityCheckQueue");
    }

    @Override
    public void addIntoValidateProxiesQueue(ProxyIp proxyIp) {
    }

    @Override
    public ProxyIp takeFromAnonymityCheckQueue() {
        return null;
    }

    @Override
    public ProxyIp takeFromValidateProxiesQueue() {
        return null;
    }

    @Override
    public void addIntoValidatedProxies(ProxyIp proxyIp) {

    }

    @Override
    public boolean existsInAnonymityCheckQueue(ProxyIp proxyIp) {
        return false;
    }

    @Override
    public boolean existsInValidateProxiesQueue(ProxyIp proxyIp) {
        return false;
    }

    @Override
    public boolean existsInValidatedProxies(ProxyIp proxyIp) {
        return false;
    }
}
