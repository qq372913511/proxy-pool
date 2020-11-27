package com.linzeming.proxypool.crawler;

import com.linzeming.proxypool.crawler.entity.ProxyIp;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class TestJsoupFreeProxyList {
    public static void main(String[] args) throws IOException {
        Document document = Jsoup.connect("https://free-proxy-list.net/").get();
        String title = document.title();
        System.out.println(title);

        Elements elements = document.getElementById("proxylisttable").getElementsByTag("tr");

        System.out.println(elements.size());
        for (int i = 1; i < elements.size() - 1; i++) {
            try {
                Element element = elements.get(i);
                ProxyIp proxyIp = new ProxyIp();
                proxyIp.setIp(element.child(0).text());
                proxyIp.setPort(Integer.parseInt(element.child(1).text()));
                System.out.println(proxyIp);
            } catch (Exception e) {
                System.out.println("exception");
            }
        }

    }
}
