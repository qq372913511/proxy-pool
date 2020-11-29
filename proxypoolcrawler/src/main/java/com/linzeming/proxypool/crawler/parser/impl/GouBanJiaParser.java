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
 * https://github.com/fengzhizi715/ProxyPool/blob/master/proxypool/src/main/java/com/cv4j/proxy/site/GoubanjiaProxyListPageParser.java
 */

@Component
public class GouBanJiaParser implements Parser {
    /**
     * 这个网页和data5U是一样的
     * @return
     */
    @Override
    public boolean isEnable() {
        return false; // todo获取的端口和网页上的版本不同 被加密过的，源码和渲染后的结果不一样
    }

    @Override
    public List<String> getUrls() {
        ArrayList<String> urlsList = new ArrayList<String>();
        urlsList.add("http://www.goubanjia.com/");
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
        Elements elements = document.select("#services > div > div.row > div > div > div > table > tbody > tr");
        // iter elements
        for (Element element : elements) {
            for (Element e : element.getElementsByTag("p")) {
                e.remove();
            }

//            System.out.println(element.select("td.ip").select("*"));
            //ip和port 用多个控件的值拼装为一个字符串
            String ip_port = element.select("td.ip").text().replace(" ", "");
            ProxyIp proxyIp = ProxyUtils.convertProxy(ip_port);
            proxies.add(proxyIp);
        }
        return proxies;
    }

    public static void main(String[] args) throws IOException {
        GouBanJiaParser data5uParser = new GouBanJiaParser();
        List<String> urls = data5uParser.getUrls();
        Document document = Jsoup.connect(urls.get(0)).get();
        System.out.println(data5uParser.parseDocument(document));
    }
}
