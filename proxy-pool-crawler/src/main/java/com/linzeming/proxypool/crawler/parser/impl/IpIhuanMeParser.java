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


@Component
public class IpIhuanMeParser implements Parser {
    @Override
    public boolean isEnable() {
        return true;
    }

    @Override
    public List<String> getUrls() {
        ArrayList<String> urlsList = new ArrayList<String>();
        urlsList.add("https://ip.ihuan.me/");
        urlsList.add("https://ip.ihuan.me/?page=4ce63706");
        urlsList.add("https://ip.ihuan.me/?page=5crfe930");
        urlsList.add("https://ip.ihuan.me/?page=f3k1d581");
        urlsList.add("https://ip.ihuan.me/?page=ce1d45977");
        urlsList.add("https://ip.ihuan.me/?page=881aaf7b5");
        urlsList.add("https://ip.ihuan.me/?page=eas7a436");
        urlsList.add("https://ip.ihuan.me/?page=981o917f5");
        urlsList.add("https://ip.ihuan.me/?page=2d28bd81a");
        urlsList.add("https://ip.ihuan.me/?page=a42g5985d");
        return urlsList;
    }
    @Override
    public String getCron() {
        return "* 0/2 * * * *";
    }

    @Override
    public List<ProxyIp> parseDocument(Document document) {
        // new a arraylist to storage proxy
        ArrayList<ProxyIp> proxies = new ArrayList<>();
        // get all elements from proxy info
        Elements elements = document.select("body > div.col-md-10 > div.table-responsive > table > tbody > tr");
        // iter elements
        for (Element element : elements){
            ProxyIp proxyIp = new ProxyIp();
            String ip = element.select("td:nth-child(1) > a").text();
            String port  = element.select("td:nth-child(2)").text();
            proxyIp.setIp(ip);
            proxyIp.setPort(Integer.parseInt(port));
            proxies.add(proxyIp);
        }
        return proxies;
    }

    public static void main(String[] args) throws IOException {
        IpIhuanMeParser data5uParser = new IpIhuanMeParser();
        List<String> urls = data5uParser.getUrls();
        Document document = Jsoup.connect(urls.get(0)).get();
        System.out.println(data5uParser.parseDocument(document));

    }
}
