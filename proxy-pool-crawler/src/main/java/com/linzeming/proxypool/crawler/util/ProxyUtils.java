package com.linzeming.proxypool.crawler.util;

import com.linzeming.proxypool.crawler.entity.ProxyIp;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;


public final class ProxyUtils {
    /**
     * 转换文本型的ProxyString到对象
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

    public static List<ProxyIp> convertProxysStringSetToList(Set<String> proxyStrings) {
        if (proxyStrings == null) return null;
        ArrayList<ProxyIp> rtnList = new ArrayList<>();
        for (String proxyString : proxyStrings) {
            rtnList.add(convertProxy(proxyString));
        }
        return rtnList;
    }

    /**
     * 验证匿名性静态方法
     * a.如果origin中包含自己真实的IP，则为透明代理；
     * b.如果响应中包含Proxy-Connection参数，则为匿名代理；
     * c.其他情况则为高匿代理；
     *
     * @param proxyIp
     * @return
     */
    public static boolean validateProxy(ProxyIp proxyIp) {
        Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(proxyIp.getIp(), proxyIp.getPort()));
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(Constants.validateTimeout, TimeUnit.SECONDS)
                .proxy(proxy)
                .build();

        Request request = new Request.Builder()
                .url("http://httpbin.org/get")
//                .header("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/68.0.3440.106 Safari/537.36")
                .build();

        Response response = null;
        try {
            response = client.newCall(request).execute();
            String resString = response.body().string();
            //如果 "origin": "91.205.174.26", 在文本中找到 则认为是匿名proxy
            return resString.contains("\"origin\": \"" + proxyIp.getIp() + "\"");
        } catch (IOException e) {
//            e.printStackTrace();
            return false;
        }
    }

}