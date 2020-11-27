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
 * https://github.com/fengzhizi715/ProxyPool/blob/master/proxypool/src/main/java/com/cv4j/proxy/site/KuaidailiProxyListPageParser.java
 */
@Component
public class KuaiDaiLi implements Parser {
    @Override
    public boolean isEnable() {
        return true;
    }

    @Override
    public List<String> getUrls() {
        ArrayList<String> urlsList = new ArrayList<String>();
        urlsList.add("https://www.kuaidaili.com/free/");
//        urlsList.add("https://www.kuaidaili.com/free/inha/2/"); //todo 会出问题
//        urlsList.add("https://www.kuaidaili.com/free/inha/3/");
//        urlsList.add("https://www.kuaidaili.com/free/inha/4/");
//        urlsList.add("https://www.kuaidaili.com/free/inha/5/");
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
        Elements elements = document.select("#list > table > tbody > tr");
        // iter elements
        for (Element element : elements){
            String ip = element.select("td:nth-child(1)").text();
            String port = element.select("td:nth-child(2)").text();
            ProxyIp proxyIp = new ProxyIp();
            proxyIp.setIp(ip);
            proxyIp.setPort(Integer.parseInt(port));
            proxies.add(proxyIp);
        }
        return proxies;
    }

    public static void main(String[] args) throws IOException {
        KuaiDaiLi data5uParser = new KuaiDaiLi();
        List<String> urls = data5uParser.getUrls();
        Document document = Jsoup.connect(urls.get(0)).get();
        System.out.println(data5uParser.parseDocument(document));

    }
}
