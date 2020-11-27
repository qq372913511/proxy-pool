package com.linzeming.proxypoolweb;

import com.linzeming.proxypoolweb.dao.ProxyIpDao;
import com.linzeming.proxypoolweb.service.ProxyIpService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import redis.clients.jedis.JedisPool;

@SpringBootTest
class ProxyPoolWebApplicationTests {
    @Autowired
    ProxyIpDao proxyIpDao;
    @Autowired
    ProxyIpService proxyIpService;


    @Test
    void contextLoads() {
        System.out.println(proxyIpDao.getAllProxyByScoreRange(10.0, 10.0).size());
        System.out.println(proxyIpService.getAvailableProxy(10));
    }

}
