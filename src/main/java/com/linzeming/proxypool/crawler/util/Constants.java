package com.linzeming.proxypool.crawler.util;

public class Constants {
    private Constants() {
    }

    public static final String redisKeyPrefix = "proxypool";
    // proxy检查队列
    public static final String redisValidateProxiesQueueSet = redisKeyPrefix + ":" + "validateProxiesQueue";
    // 已经检查过匿名性的proxy的存放zset score用于排名连接性
    public static final String redisValidatedProxiesZset = redisKeyPrefix + ":" + "proxiesZset";
    // 记录ip上次验证的时间
    public static final String redisProxiesValidateLastTimeHashes = redisKeyPrefix + ":" + "proxiesValidateLastTimeHashes";
    // 记录ip所有验证的时间
    public static final String redisProxiesValidateAllTimeList = redisKeyPrefix + ":" + "proxiesValidateAllResult";
    // 验证透明性后的代理 插入到validated Proxies列表中的初始分数
    public static final Double validatedProxyInitScore = 5.0;
    // 最大分数
    public static final Double validatedProxyMaxScore = 10.0;
    // 验证匿名性的超时时间 单位是秒
    public static final int validateTimeout = 10;
    // 线程数量
    public static final int ValidateProxyIpQueueConsumerThreadCount = 5000;
    //代理最低验证间隔
    public static final Integer proxyExpireSecond = 2 * 60; // proxy的过期时间, 上次验证时间离现在超过这个时长则拉下来验证。
    // 验证队列阈值，低于阈值触发插入
    public static final Integer queueThreshold = ValidateProxyIpQueueConsumerThreadCount; // 队列size低于这个阈值则开始从redis中获取过期的proxy
    // 队列检查时间间隔
    public static final Integer refreshInterval = validateTimeout / 2 * 1000; // 每几秒运行一次
    // 插入校验队列的最低分数
    public static final Double insertIntoValidateQueueMinScore = -999.0;
    // 插入校验队列的最高分数
    public static final Double insertIntoValidateQueueMaxScore = validatedProxyMaxScore;

}
