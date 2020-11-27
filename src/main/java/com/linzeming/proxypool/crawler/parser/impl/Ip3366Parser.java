package com.linzeming.proxypool.crawler.parser.impl;

import com.linzeming.proxypool.crawler.entity.ProxyIp;
import com.linzeming.proxypool.crawler.parser.Parser;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


/**
 * https://github.com/fengzhizi715/ProxyPool/blob/master/proxypool/src/main/java/com/cv4j/proxy/site/Ip3366ProxyListPageParser.java
 */

@Component
public class Ip3366Parser implements Parser {
    @Override
    public boolean isEnable() {
        return true;
    }

    @Override
    public List<String> getUrls() {
        ArrayList<String> urlsList = new ArrayList<String>();
        urlsList.add("http://www.ip3366.net/");
        return urlsList;
    }
    @Override
    public String getCron() {
        return "* 0/1 * * * *";
    }

    @Override
    public List<ProxyIp> parseDocument(Document document) {
        // new a arraylist to storage proxy
        ArrayList<ProxyIp> proxies = new ArrayList<>();
        // get all elements from proxy info
        Elements elements = document.select("div[id=list] table tbody tr");
        // iter elements
        int i = 0;
        for (Element element : elements){
            if(i == 0) {
                i++;
                continue;
            }
            ProxyIp proxyIp = new ProxyIp();
            String ip = element.select("td:eq(0)").first().text();
            String port  = element.select("td:eq(1)").first().text();
            proxyIp.setIp(ip);
            proxyIp.setPort(Integer.parseInt(port));
            proxies.add(proxyIp);
        }
        return proxies;
    }

    public static void main(String[] args) throws IOException {
        Ip3366Parser data5uParser = new Ip3366Parser();
        List<String> urls = data5uParser.getUrls();
        Document document = Jsoup.connect(urls.get(0)).get();
        System.out.println(data5uParser.parseDocument(document));

    }
}
