package com.linzeming.proxypoolweb.util;

public class Constants {
    private Constants() {
    }

    public static final String redisKeyPrefix = "proxypool";
    // proxy检查队列
    public static final String redisValidateProxiesQueueList = redisKeyPrefix + ":" + "validateProxiesQueue";
    //redis线程池最大线程数量
    public static final int redisPoolMaxTotal = 20;
    //datasource配置
    public static final int dataSourceMaxActive = 10;
    public static final int dataSourceMaxWait = 30000;
    //Validate Log Key Prefix
    public static final String validateLogKeyPrefix = redisKeyPrefix + ":" + "validateLog";



}
