package com.linzeming.proxypool.crawler.parser;

import com.linzeming.proxypool.crawler.entity.ProxyIp;
import org.jsoup.nodes.Document;

import java.util.List;

public interface Parser {
    boolean isEnable();
    List<String> getUrls();
    String getCron();
    List<ProxyIp> parseDocument(Document document);


}
