package com.linzeming.proxypool.crawler.util;

import com.linzeming.proxypool.crawler.Starter;
import com.linzeming.proxypool.crawler.model.ProxyIp;
import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.exception.GeoIp2Exception;
import com.maxmind.geoip2.model.CityResponse;
import com.maxmind.geoip2.record.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class Geo {
    final static Logger logger = LoggerFactory.getLogger(Geo.class);
    static DatabaseReader reader;

    static {
        try {
            InputStream inputStream = new ClassPathResource("GeoLite2-City.mmdb").getInputStream();
            reader = new DatabaseReader.Builder(inputStream).build();
            logger.info("Geo database load infomation: ");
            logger.info(reader.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void completeProxyIpGeoInfo(ProxyIp proxyIp) {
        //判断reader是否加载成功
        if (reader == null) {
            logger.warn("reader load failed!");
            proxyIp.setCountry("");
            proxyIp.setCity("");
            return;
        }

        // get ipAddress info
        InetAddress ipAddress;
        try {
            ipAddress = InetAddress.getByName(proxyIp.getIp());
        } catch (UnknownHostException e) {
            //如果在数据库中不存在，则直接返回空国家和空城市
            proxyIp.setCountry("");
            proxyIp.setCity("");
            return;
        }
        if (ipAddress == null) {
            proxyIp.setCountry("");
            proxyIp.setCity("");
            return;
        }

        // get response
        CityResponse response = null;
        try {
            response = reader.city(ipAddress);
        } catch (IOException | GeoIp2Exception e) {
            //如果在查询出问题，则直接返回
            proxyIp.setCountry("");
            proxyIp.setCity("");
            return;
        }
        if (response == null) {
            proxyIp.setCountry("");
            proxyIp.setCity("");
            return;
        }

        // get country
        String country = response.getCountry().getNames().get("zh-CN");
        if (country == null) {
            country = response.getCountry().getName();
        }
        if (country == null) {
            proxyIp.setCountry("");
        } else {
            proxyIp.setCountry(country);
        }
        logger.debug(proxyIp.getCountry());

        // get city
        String city = response.getCity().getNames().get("zh-CN");
        if (city == null) {
            city = response.getCity().getName();
        }
        if (city == null) {
            proxyIp.setCity("");
        }else{
            proxyIp.setCity(city);
        }
        logger.debug(proxyIp.getCity());

    }

}
