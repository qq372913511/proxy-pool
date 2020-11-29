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

/**
 * https://github.com/fengzhizi715/ProxyPool/blob/master/proxypool/src/main/java/com/cv4j/proxy/site/KuaidailiProxyListPageParser.java
 */
@Component
public class ListProxyListPlusParser implements Parser {
    @Override
    public boolean isEnable() {
        return true;
    }

    @Override
    public List<String> getUrls() {
        ArrayList<String> urlsList = new ArrayList<String>();
        urlsList.add("https://list.proxylistplus.com/Fresh-HTTP-Proxy-List-1");
        urlsList.add("https://list.proxylistplus.com/Fresh-HTTP-Proxy-List-2");
        urlsList.add("https://list.proxylistplus.com/Fresh-HTTP-Proxy-List-3");
        urlsList.add("https://list.proxylistplus.com/Fresh-HTTP-Proxy-List-4");
        urlsList.add("https://list.proxylistplus.com/Fresh-HTTP-Proxy-List-5");
        return urlsList; //todo 似乎全部都是透明代理
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
        Elements elements = document.select("#page > table.bg > tbody > tr");
        // iter elements
        int n =0;
        for (Element element : elements){
            n++;
            if(n<3){
                continue;
            }
            String ip = element.select("td:nth-child(2)").text().replace(" ","");
            String port = element.select("td:nth-child(3)").text().replace(" ","");
            ProxyIp proxyIp = new ProxyIp(ip, port);
            proxies.add(proxyIp);
        }
        return proxies;
    }

    public static void main(String[] args) throws IOException {
        ListProxyListPlusParser data5uParser = new ListProxyListPlusParser();
        List<String> urls = data5uParser.getUrls();
        Document document = Jsoup.connect(urls.get(0)).get();
        System.out.println(data5uParser.parseDocument(document));

    }
}
