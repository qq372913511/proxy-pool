package com.linzeming.proxypoolweb;

import com.linzeming.proxypoolweb.service.ProxyIpService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import redis.clients.jedis.JedisPool;

@SpringBootTest
class ProxyPoolWebApplicationTests {
    @Autowired
    ProxyIpService proxyIpService;


    @Test
    void contextLoads() {
    }

}
