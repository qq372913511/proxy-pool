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
 * https://github.com/fengzhizi715/ProxyPool/blob/master/proxypool/src/main/java/com/cv4j/proxy/site/Data4uProxyListPageParser.java
 */
@Component
public class Data5uParser implements Parser {
    @Override
    public boolean isEnable() {
        return true;
    }

    @Override
    public List<String> getUrls() {
        ArrayList<String> urlsList = new ArrayList<String>();
        urlsList.add("http://www.data5u.com/");
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
        Elements elements = document.select("div[class=wlist] li:eq(1) ul");
        // iter elements
        int i =0;
        for (Element element : elements){
            i++;
            if (i ==1){
                continue;
            }
            ProxyIp proxyIp = new ProxyIp();
            String ip = element.select("span:eq(0) > li").text();
            String portClass = element.select("span:eq(1) > li").attr("class").replace("port ","");
            int port = decryptPort(portClass);
            proxyIp.setIp(ip);
            proxyIp.setPort(port);
            proxies.add(proxyIp);
        }
        return proxies;
    }

    public static void main(String[] args) throws IOException {
        Data5uParser data5uParser = new Data5uParser();
        List<String> urls = data5uParser.getUrls();
        Document document = Jsoup.connect(urls.get(0)).get();
        System.out.println(data5uParser.parseDocument(document));

    }

    private int decryptPort(String portClass){
        //端口加密了 https://zhuanlan.zhihu.com/p/84361860
        /*
         * port = port_class.replace('A', '0').replace('B', '1').replace('C', '2').replace('D', '3').replace('E', '4').replace('F', '5').replace('G', '6').replace('H', '7').replace('I', '8').replace('Z', '9')
         * proxy['port'] = int(int(port) / 8)
         */
        String port_8 = portClass.replace('A', '0').replace('B', '1').replace('C', '2').replace('D', '3').replace('E', '4').replace('F', '5').replace('G', '6').replace('H', '7').replace('I', '8').replace('Z', '9');
        return Integer.parseInt(port_8) / 8;
    }
}
