package com.linzeming.proxypool.crawler;

import com.linzeming.proxypool.crawler.util.ProxyUtils;
import org.junit.Test;

public class TestFormatIp {
    @Test
    public void test1(){
        System.out.println(ProxyUtils.formatIp("001.001.01.01"));
    }
}
