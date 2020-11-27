package com.linzeming.proxypool.crawler.parser.impl;

import com.linzeming.proxypool.crawler.entity.ProxyIp;
import com.linzeming.proxypool.crawler.parser.Parser;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;


/**
 *
 */

@Component
public class FreeProxyListParser implements Parser {
    @Override
    public boolean isEnable() {
        return true;
    }

    @Override
    public List<String> getUrls() {
        ArrayList<String> urlsList = new ArrayList<String>();
        urlsList.add("https://free-proxy-list.net/");
        return urlsList;
    }

    @Override
    public String getCron() {
        return "0/30 * * * * *";
    }


    @Override
    public List<ProxyIp> parseDocument(Document document) {
        // new a arraylist to storage proxy
        ArrayList<ProxyIp> proxies = new ArrayList<>();
        // get all elements from proxy info
        Elements elements = document.getElementById("proxylisttable").getElementsByTag("tr");
        // iter elements
        for (int i = 1; i < elements.size() - 1; i++) {
            try {
                Element element = elements.get(i);
                ProxyIp proxyIp = new ProxyIp();
                proxyIp.setIp(element.child(0).text());
                proxyIp.setPort(Integer.parseInt(element.child(1).text()));
                proxies.add(proxyIp);
            } catch (Exception e) {
                System.out.println("exception");
            }
        }
        return proxies;
    }
}
