package com.linzeming.proxypool.crawler.dao;

import com.linzeming.proxypool.crawler.entity.ProxyIp;

import java.util.Set;

public interface ProxyIpDao {
    void addIntoAnonymityCheckQueue(ProxyIp proxyIp);

    void addIntoValidateProxiesQueue(ProxyIp proxyIp);

    void addIntoBlackSet(ProxyIp proxyIp);

    ProxyIp takeFromAnonymityCheckQueue();

    ProxyIp takeFromValidateProxiesQueue();

    void addIntoValidatedProxies(ProxyIp proxyIp);

    boolean existsInAnonymityCheckQueue(ProxyIp proxyIp);

    boolean existsInValidateProxiesQueue(ProxyIp proxyIp);

    boolean existsInValidatedProxies(ProxyIp proxyIp);

    boolean existsInBlackSet(ProxyIp proxyIp);

    void setValidateTime(ProxyIp proxyIp, long validateTime);

    long getValidateTime(ProxyIp proxyIp);

    long countValidateProxiesQueue();

    Set<String> getRangeValidatedProxiesByScore(Double minScore, Double maxScore);

    double getValidatedProxyScore(ProxyIp proxyip);

    void decreaseValidatedProxyScore(ProxyIp proxyIp, double deltaScore);

    void increaseValidatedProxyScore(ProxyIp proxyIp, double deltaScore);

    void setValidatedProxyScore(ProxyIp proxyIp, double targerScore);

}
