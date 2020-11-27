package com.linzeming.proxypool.crawler.parser.impl;

import com.linzeming.proxypool.crawler.entity.ProxyIp;
import com.linzeming.proxypool.crawler.parser.Parser;
import com.linzeming.proxypool.crawler.util.ProxyUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


@Component
public class ApiProxyscrapeParser implements Parser {
    @Override
    public boolean isEnable() {
        return true;
    }

    @Override
    public List<String> getUrls() {
        ArrayList<String> urlsList = new ArrayList<String>();
        urlsList.add("https://api.proxyscrape.com/v2/?request=getproxies&protocol=http&timeout=10000&country=all&ssl=all&anonymity=elite&simplified=true");
        urlsList.add("https://api.proxyscrape.com/v2/?request=getproxies&protocol=http&timeout=10000&country=all&ssl=all&anonymity=anonymous&simplified=true");
        return urlsList;
    }

    @Override
    public String getCron() {
        return "* 0/5 * * * *";
    }

    @Override
    public List<ProxyIp> parseDocument(Document document) {
        // new a arraylist to storage proxy
        ArrayList<ProxyIp> proxies = new ArrayList<>();
        // get all elements from proxy info

        String body = document.select("body").text();
        String[] s = body.split(" ");
        for (String ipPort: s) {
            if (ipPort.contains(":")) {
                ProxyIp proxyIp = ProxyUtils.convertProxy(ipPort);
                proxies.add(proxyIp);
            }
        }
        return proxies;
    }

    public static void main(String[] args) throws IOException {
        ApiProxyscrapeParser data5uParser = new ApiProxyscrapeParser();
        List<String> urls = data5uParser.getUrls();
        for (int i = 0; i < 2; i++) {
            Document document = Jsoup.connect(urls.get(i)).get();
            List<ProxyIp> proxyIps = data5uParser.parseDocument(document);
            System.out.println(proxyIps.size());
            System.out.println(proxyIps);
        }

    }
}
