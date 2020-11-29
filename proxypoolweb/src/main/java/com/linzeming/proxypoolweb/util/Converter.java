package com.linzeming.proxypoolweb.util;

import com.linzeming.proxypoolweb.model.ProxyIp;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import redis.clients.jedis.Tuple;


public class Converter {
    public static ProxyIp convertProxy(String proxyString) {
        if (proxyString == null) return null;
        if (proxyString.contains(":")) {
            String s1 = proxyString.split(":")[0];
            String s2 = proxyString.split(":")[1];
            ProxyIp proxyIp = new ProxyIp(s1, Integer.parseInt(s2));
            return proxyIp;
        }
        return null;
    }

    public static List<ProxyIp> convertProxyiesStringSetToList(Set<String> proxyStrings) {
        if (proxyStrings == null) return null;
        ArrayList<ProxyIp> rtnList = new ArrayList<>();
        for (String proxyString : proxyStrings) {
            rtnList.add(convertProxy(proxyString));
        }
        return rtnList;
    }
    public static Set<ProxyIp> convertProxiesStringSetToSet(Set<String> proxyStrings) {
        if (proxyStrings == null) return null;
        HashSet<ProxyIp> rtnSet = new HashSet<>();
        for (String proxyString : proxyStrings) {
            rtnSet.add(convertProxy(proxyString));
        }
        return rtnSet;
    }

    public static List<ProxyIp> convertProxiesWithScoreTuplesToList(Set<Tuple> tuples){
        ArrayList<ProxyIp> rtnList = new ArrayList<>();
        for (Tuple tuple : tuples) {
            ProxyIp proxyIp = Converter.convertProxy(tuple.getElement());
            proxyIp.setScore(tuple.getScore());
            if (tuple.getScore() < Constants.validatedProxyMaxScore) {
                proxyIp.setLastValidateResult(false);
            } else {
                proxyIp.setLastValidateResult(true);
            }
            rtnList.add(proxyIp);
        }
        return rtnList;
    }

    public static List<String> convertProxiesToIpPortStrings(List<ProxyIp> proxyIps){
        if (proxyIps == null) {
            return null;
        }
        ArrayList<String> strings = new ArrayList<>();
        for(ProxyIp proxyIp:proxyIps){
            strings.add(proxyIp.formattedIpPort());
        }
        return strings;
    }
}
