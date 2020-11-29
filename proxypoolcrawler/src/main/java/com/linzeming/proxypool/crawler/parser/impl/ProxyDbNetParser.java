package com.linzeming.proxypool.crawler.parser.impl;

import com.linzeming.proxypool.crawler.model.ProxyIp;
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

/**
 * https://github.com/fengzhizi715/ProxyPool/blob/master/proxypool/src/main/java/com/cv4j/proxy/site/KuaidailiProxyListPageParser.java
 */
@Component
public class ProxyDbNetParser implements Parser {
    @Override
    public boolean isEnable() {
        return true;
    }

    @Override
    public List<String> getUrls() {
        ArrayList<String> urlsList = new ArrayList<String>();
        urlsList.add("http://proxydb.net/?protocol=http&protocol=https&anonlvl=2&anonlvl=3&anonlvl=4&country=");
        urlsList.add("http://proxydb.net/?protocol=http&protocol=https&anonlvl=2&anonlvl=3&anonlvl=4&country=BD");
        urlsList.add("http://proxydb.net/?protocol=http&protocol=https&anonlvl=2&anonlvl=3&anonlvl=4&country=ID");
        urlsList.add("http://proxydb.net/?protocol=http&protocol=https&anonlvl=2&anonlvl=3&anonlvl=4&country=US");
        urlsList.add("http://proxydb.net/?protocol=http&protocol=https&anonlvl=2&anonlvl=3&anonlvl=4&country=TH");
        urlsList.add("http://proxydb.net/?protocol=http&protocol=https&anonlvl=2&anonlvl=3&anonlvl=4&country=KR");
        urlsList.add("http://proxydb.net/?protocol=http&protocol=https&anonlvl=2&anonlvl=3&anonlvl=4&country=CN");
        urlsList.add("http://proxydb.net/?protocol=http&protocol=https&anonlvl=2&anonlvl=3&anonlvl=4&country=HK");
        // todo 可以将所有国家都加进去，只获取第一页，获取第二页需要key
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
        Elements elements = document.select("body > div.container-fluid > div > table > tbody > tr");
        // iter elements
        for (Element element : elements){
            String ipPort = element.select("td:nth-child(1) > a").text().replace(" ","");
            ProxyIp proxyIp = ProxyUtils.convertProxy(ipPort);
            proxies.add(proxyIp);
        }
        return proxies;
    }

    public static void main(String[] args) throws IOException {
        ProxyDbNetParser data5uParser = new ProxyDbNetParser();
        List<String> urls = data5uParser.getUrls();
        Document document = Jsoup.connect(urls.get(1)).get();
        System.out.println(data5uParser.parseDocument(document));

    }
}
