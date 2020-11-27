package com.linzeming.proxypool.crawler;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.concurrent.TimeUnit;

public class TestHttpClients {
    public static void main(String[] args) throws IOException {
        String proxyAddress = "123.55.101.191";
        int proxyPort = 9999;
        Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(proxyAddress, proxyPort));
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(20, TimeUnit.SECONDS)
                .proxy(proxy)
                .build();

        Request request = new Request.Builder()
//                .url("http://httpbin.org/get")
                .url("http://github.com")
                .header("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/68.0.3440.106 Safari/537.36")
                .build();

        Response response = client.newCall(request).execute();
        System.out.println(response.body().string());


    }
}
