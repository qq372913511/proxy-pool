package com.linzeming.proxypool.crawler.parser.impl;

import com.linzeming.proxypool.crawler.model.ProxyIp;
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
public class IpJiangXianLiParser implements Parser {
    @Override
    public boolean isEnable() {
        return true;
    }

    @Override
    public List<String> getUrls() {
        ArrayList<String> urlsList = new ArrayList<String>();
        urlsList.add("https://ip.jiangxianli.com/?page=1");
        urlsList.add("https://ip.jiangxianli.com/?page=2");
        urlsList.add("https://ip.jiangxianli.com/?page=3");
        urlsList.add("https://ip.jiangxianli.com/?page=4");
        urlsList.add("https://ip.jiangxianli.com/?page=5");
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
        Elements elements = document.select("body > div.layui-layout.layui-layout-admin > div.layui-row > div.layui-col-md9.ip-tables > div.layui-form > table > tbody > tr");
        // iter elements
        for (Element element : elements){
            ProxyIp proxyIp = new ProxyIp();
            String ip = element.select("td:nth-child(1)").text();
            String port  = element.select("td:nth-child(2)").text();
            proxyIp.setIp(ip);
            proxyIp.setPort(Integer.parseInt(port));
            proxies.add(proxyIp);
        }
        return proxies;
    }


    public static void main(String[] args) throws IOException {
        IpJiangXianLiParser data5uParser = new IpJiangXianLiParser();
        List<String> urls = data5uParser.getUrls();
        Document document = Jsoup.connect(urls.get(0)).get();
        System.out.println(data5uParser.parseDocument(document));
        System.out.println();
    }

}
