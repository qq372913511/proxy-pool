package com.linzeming.proxypool.crawler.util;

public class Constants {
    private Constants() {
    }

    public static final String redisKeyPrefix = "proxypool";
    // proxy检查队列
    public static final String redisValidateProxiesQueueList = redisKeyPrefix + ":" + "validateProxiesQueue";
    // 验证匿名性的超时时间 单位是秒
    public static final int validateTimeout = 10;
    // 线程数量
    public static final int ValidateProxyIpQueueConsumerThreadCount = 5000;
    //redis线程池最大线程数量
    public static final int redisPoolMaxTotal = 5000;
    //代理最低验证间隔
    public static final Integer proxyExpireSecond = 2 * 60; // proxy的过期时间, 上次验证时间离现在超过这个时长则拉下来验证。
    // 验证队列阈值，低于阈值触发插入
    public static final Integer queueThreshold = ValidateProxyIpQueueConsumerThreadCount; // 队列size低于这个阈值则开始从redis中获取过期的proxy
    //定时验证，插入队列间隔时间
    public static final Integer validateProxyIpInterval = 3 * 60 * 1000; // 2min
    //自己的IP todo 后期要改成动态获取
    public static final String selfIp = "185.5.11.64";
    //Validate Log Key Prefix
    public static final String validateLogKeyPrefix = redisKeyPrefix + ":" + "validateLog";
    //datasource配置
    public static final int dataSourceMaxActive = 5000;
    public static final int dataSourceMaxWait = 30000;

}
