package com.linzeming.proxypool.crawler.util;

import com.linzeming.proxypool.crawler.Starter;
import com.linzeming.proxypool.crawler.model.ProxyIp;
import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.exception.GeoIp2Exception;
import com.maxmind.geoip2.model.CityResponse;
import com.maxmind.geoip2.record.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class Geo {
    final static Logger logger = LoggerFactory.getLogger(Geo.class);

    // A File object pointing to your GeoIP2 or GeoLite2 database
    static File database = new File("C:\\Users\\linze\\Desktop\\GeoLite2-City_20201208.tar\\GeoLite2-City_20201208\\GeoLite2-City.mmdb");
    // This creates the DatabaseReader object. To improve performance, reuse
// the object across lookups. The object is thread-safe.
    static DatabaseReader reader;

    static {
        try {
            reader = new DatabaseReader.Builder(database).build();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void completeProxyIpGeoInfo(ProxyIp proxyIp) {
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
