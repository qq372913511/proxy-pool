package com.linzeming.proxypool.crawler.service.impl;

import com.linzeming.proxypool.crawler.dao.ProxyIpDao;
import com.linzeming.proxypool.crawler.service.ProxyIpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service("proxyService")
public class ProxyIpServiceImpl implements ProxyIpService {
    @Autowired
    ProxyIpDao proxyIpDao;
}
