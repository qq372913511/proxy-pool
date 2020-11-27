package com.linzeming.proxypool.crawler.dao;

import com.linzeming.proxypool.crawler.entity.ProxyIp;

import java.util.List;
import java.util.Set;

public interface ProxyIpDao {
    void addIntoValidateProxiesQueue(ProxyIp proxyIp);

    void addIntoValidateProxiesQueueMany(List<ProxyIp> proxyIps);

    ProxyIp takeFromValidateProxiesQueue();

    void addIntoProxiesZsetNx(ProxyIp proxyIp);

    boolean existsInValidateProxiesQueue(ProxyIp proxyIp);

    boolean existsInProxiesZset(ProxyIp proxyIp);

    void setLastValidateTime(ProxyIp proxyIp);

    long getLastValidateTime(ProxyIp proxyIp);

    long countValidateProxiesQueue();

    Set<String> getRangeProxiesByScore(Double minScore, Double maxScore);

    double getProxyScore(ProxyIp proxyip);

    void decreaseProxyScore(ProxyIp proxyIp, double deltaScore);

    void increaseProxyScore(ProxyIp proxyIp, double deltaScore);

    void setProxyScore(ProxyIp proxyIp);

    void addValidatePorxyResult(ProxyIp proxyIp);

}
