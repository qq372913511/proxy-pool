package com.linzeming.proxypool.crawler.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.linzeming.proxypool.crawler.model.ProxyIp;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;


public final class ProxyUtils {
    /**
     * 转换文本型的ProxyString 以冒号分隔的 到对象 供给parser使用
     *
     * @param proxyString
     * @return
     */
    public static ProxyIp convertProxy(String proxyString) {
        if (proxyString == null) return null;
        if (proxyString.contains(":")) {
            String s1 = proxyString.split(":")[0];
            String s2 = proxyString.split(":")[1];
            ProxyIp proxyIp = new ProxyIp(s1, Integer.parseInt(s2));
            return proxyIp;
        }
        return null;
    }

    /**
     * 验证匿名性静态方法
     * 完全不行 返回值
     * a.如果origin中包含自己真实的IP，则为透明代理；
     * b.如果响应中包含Proxy-Connection参数，则为匿名代理；
     * c.其他情况则为高匿代理；
     * 所有信息写回去传进来的对象
     *
     * @param proxyIp
     * @return
     */
    public static void validateProxy(ProxyIp proxyIp) {
        //设置连接
        Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(proxyIp.getIp(), proxyIp.getPort()));
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(Constants.validateTimeout, TimeUnit.SECONDS)
                .proxy(proxy)
                .build();

        //新建一个请求
        Request request = new Request.Builder()
                .url("http://httpbin.org/get")
                .build();
        Response response = null;
        //验证前设置验证时间
        proxyIp.setGmtLastValidate(LocalDateTime.now());
        //开始验证http请求
        try {
            // get 请求
            long starttime = System.currentTimeMillis();
            response = client.newCall(request).execute();
            long endtime = System.currentTimeMillis();
            long duration = endtime - starttime;
            int durationInt = (int) duration;
            proxyIp.setConnectionSpeed(durationInt);

            // 提取返回的文本
            String resString = response.body().string();
            //解析JSON
            JSONObject jsonObject = JSON.parseObject(resString);
            //验证自己的IP是否在origin中
            String origin = jsonObject.getString("origin");
            if (origin.contains(Constants.selfIp)) {
                proxyIp.setAnonymity(0);
            }
            if (resString.toLowerCase().contains("proxy-connection")) {
                proxyIp.setAnonymity(1);
            } else {
                proxyIp.setAnonymity(2);
            }
        } catch (IOException e) {
            //超时或者端口关闭
            proxyIp.setConnectionSpeed(0);
            proxyIp.setAnonymity(0);
            return;
        }

        //新建一个https请求
        Request requestHttps = new Request.Builder()
                .url("http://httpbin.org/get")
                .build();
        Response responseHttps = null;
        //开始验证https请求
        try {
            response = client.newCall(requestHttps).execute();
            // 提取返回的文本
            String resString = response.body().string();
            if (resString.contains("\"origin\"")) {
                proxyIp.setIsHttps(1);
            } else {
                proxyIp.setIsHttps(0);
            }
        } catch (IOException e) {
            proxyIp.setIsHttps(0);
        }

    }


    /**
     * 格式化ip，有时候ip会以0开头，要去掉
     *
     * @param ip
     * @return
     */
    public static String formatIp(String ip) {
        while (ip.startsWith("0")) {
            ip = ip.substring(1);
        }
//        while (ip.contains(".0")) {
//            ip = ip.replace(".0", ".");
//        }
        return ip;

    }

}