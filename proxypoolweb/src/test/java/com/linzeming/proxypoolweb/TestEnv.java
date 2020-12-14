package com.linzeming.proxypoolweb;

import com.linzeming.proxypoolweb.mapper.ProxyIpMapper;
import com.linzeming.proxypoolweb.model.ProxyIp;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestEnv {
    @Autowired
    ProxyIpMapper proxyIpMapper;


    @Test
    public void test1(){
        List<ProxyIp> proxyIps = proxyIpMapper.selectList(null);
        System.out.println(proxyIps);

    }
    @Test
    public void test2(){
        LocalDateTime now = LocalDateTime.now();

        System.out.println(now);
        Duration between = Duration.between(now, LocalDateTime.parse("2020-12-13T21:59:07.075254100"));
        System.out.println(between.toMillis());

    }
}
