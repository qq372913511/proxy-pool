package com.linzeming.proxypool.crawler;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.linzeming.proxypool.crawler.model.ProxyIp;
import org.junit.Test;

public class TestFastJson {
    @Test
    public void test1(){
        System.out.println(JSON.toJSONString(new ProxyIp("123.123.123.123", 12304)));
    }
}
