package com.linzeming.proxypool.crawler.util;

import org.jcp.xml.dsig.internal.dom.DOMUtils;

public class Constants {
    private Constants() {
    }

    public static final String redisKeyPrefix = "proxypool";
    // 匿名性检查队列
    public static final String redisAnonymityCheckQueueSet = redisKeyPrefix + ":" + "anonymityCheckQueue";
    // 连接性接茬队列
    public static final String redisValidateProxiesQueueSet = redisKeyPrefix + ":" + "validateProxiesQueue";
    // 已经检查过匿名性的proxy的存放zset score用于排名连接性
    public static final String redisValidatedProxiesZset = redisKeyPrefix + ":" + "validatedProxies";
    // 黑名单
    public static final String redisProxiesBlackSet = redisKeyPrefix + ":" + "proxiesBlackSet";
    // 记录ip上次验证的时间
    public static final String redisProxiesValidateTimeHashes = redisKeyPrefix + ":" + "proxiesValidateTimeHashes";
    // 验证透明性后的代理 插入到validated Proxies列表中的初始分数
    public static final Double validatedProxyInitScore = 10.0;
    // 最大分数
    public static final Double validatedProxyMaxScore = 10.0;
    // 最低分数 要被删除的分数
    public static final Double validatedProxyMinScore = 0.0;
    // 验证匿名性的超时时间 单位是秒
    public static final int validateAnonymityTimeout = 30;
    // 线程数量
    public static final int AnonymityCheckQueueConsumerThreadCount = 300;
    public static final int ValidateProxyIpQueueConsumerThreadCount = 100;





}
