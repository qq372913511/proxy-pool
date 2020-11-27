package com.linzeming.proxypool.crawler.parser.impl;

import com.linzeming.proxypool.crawler.entity.ProxyIp;
import com.linzeming.proxypool.crawler.parser.Parser;
import com.linzeming.proxypool.crawler.util.ProxyUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


@Component
public class XiLaDaiLiParser implements Parser {
    @Override
    public boolean isEnable() {
        return true;
    }

    @Override
    public List<String> getUrls() {
        ArrayList<String> urlsList = new ArrayList<String>();
        urlsList.add("http://www.xiladaili.com/gaoni/");
        urlsList.add("http://www.xiladaili.com/gaoni/2/");
        urlsList.add("http://www.xiladaili.com/gaoni/3/");
        urlsList.add("http://www.xiladaili.com/gaoni/4/");
        urlsList.add("http://www.xiladaili.com/gaoni/5/");
        urlsList.add("http://www.xiladaili.com/http/");
        urlsList.add("http://www.xiladaili.com/http/2/");
        urlsList.add("http://www.xiladaili.com/http/3/");
        urlsList.add("http://www.xiladaili.com/http/4/");
        urlsList.add("http://www.xiladaili.com/http/5/");
        urlsList.add("http://www.xiladaili.com/https/");
        urlsList.add("http://www.xiladaili.com/https/2/");
        urlsList.add("http://www.xiladaili.com/https/3/");
        urlsList.add("http://www.xiladaili.com/https/4/");
        urlsList.add("http://www.xiladaili.com/https/5/");
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
        Elements elements = document.select("body > div > div.container.mt-4 > div.mt-0.mb-2.table-responsive > table > tbody > tr");
        // iter elements
        for (Element element : elements){
            String ipPort = element.select("td:nth-child(1)").text();
            ProxyIp proxyIp = ProxyUtils.convertProxy(ipPort);
            proxies.add(proxyIp);
        }
        return proxies;
    }

    public static void main(String[] args) throws IOException {
        XiLaDaiLiParser data5uParser = new XiLaDaiLiParser();
        List<String> urls = data5uParser.getUrls();
        for (int i = 0; i < 15; i++) {
            Document document = Jsoup.connect(urls.get(i)).get();
            List<ProxyIp> proxyIps = data5uParser.parseDocument(document);
            System.out.println(proxyIps.size());
            System.out.println(proxyIps);
        }

    }
}
