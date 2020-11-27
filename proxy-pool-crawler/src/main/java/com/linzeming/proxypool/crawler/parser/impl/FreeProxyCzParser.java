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
public class FreeProxyCzParser implements Parser {
    @Override
    public boolean isEnable() {
        return false;
    }

    @Override
    public List<String> getUrls() {
        ArrayList<String> urlsList = new ArrayList<String>();
        urlsList.add("http://free-proxy.cz/en/proxylist/country/all/http/ping/level2");
        urlsList.add("http://free-proxy.cz/en/proxylist/country/all/http/ping/level2/2");
        urlsList.add("http://free-proxy.cz/en/proxylist/country/all/http/ping/level2/3");
        urlsList.add("http://free-proxy.cz/en/proxylist/country/all/http/ping/level2/4");
        urlsList.add("http://free-proxy.cz/en/proxylist/country/all/http/ping/level2/5");
        urlsList.add("http://free-proxy.cz/en/proxylist/country/all/http/ping/level1");
        urlsList.add("http://free-proxy.cz/en/proxylist/country/all/http/ping/level1/2");
        urlsList.add("http://free-proxy.cz/en/proxylist/country/all/http/ping/level1/3");
        urlsList.add("http://free-proxy.cz/en/proxylist/country/all/http/ping/level1/4");
        urlsList.add("http://free-proxy.cz/en/proxylist/country/all/http/ping/level1/5");
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
        Elements elements = document.select("#proxy_list > tbody > tr");
        // iter elements
        int i = 0;
        for (Element element : elements){
            if(i == 0) {
                i++;
                continue;
            }
            ProxyIp proxyIp = new ProxyIp();
            String ip = element.select("td:nth-child(1)").text();
            String port = element.select("td:nth-child(2)").text();
            System.out.println(ip);
            System.out.println(port);
            proxyIp.setIp(ip);
            proxyIp.setPort(Integer.parseInt(port));
            proxies.add(proxyIp);
        }
        return proxies;
    }

    public static void main(String[] args) throws IOException {
        FreeProxyCzParser data5uParser = new FreeProxyCzParser();
        List<String> urls = data5uParser.getUrls();
        Document document = Jsoup.connect(urls.get(0)).get();
        System.out.println(data5uParser.parseDocument(document));

    }
}
