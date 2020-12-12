package com.linzeming.proxypool.crawler;

import com.linzeming.proxypool.crawler.config.ContextMainConfig;
import com.linzeming.proxypool.crawler.dao.ProxyIpRedisDao;
import com.linzeming.proxypool.crawler.model.ProxyIp;
import com.linzeming.proxypool.crawler.util.SpringUtil;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javax.swing.*;
import java.util.ArrayList;

public class TestJedisMapper {
    private final ApplicationContext ioc = new AnnotationConfigApplicationContext(ContextMainConfig.class);


    @Test
    public void test1(){
        ProxyIpRedisDao proxyIpRedisDao = (ProxyIpRedisDao) SpringUtil.getBean("proxyIpRedisDao");
        ArrayList<ProxyIp> proxyIpArrayList = new ArrayList<>(10);
        proxyIpArrayList.add(new ProxyIp("123.123.123.123",12312));
        proxyIpArrayList.add(new ProxyIp("123.123.23.23",23));
        proxyIpRedisDao.insertProxyIpIntoValidateQueue(proxyIpArrayList);


    }
}
