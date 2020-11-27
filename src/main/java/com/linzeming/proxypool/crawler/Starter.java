package com.linzeming.proxypool.crawler;

import com.linzeming.proxypool.crawler.config.ContextMainConfig;
import com.linzeming.proxypool.crawler.entity.ProxyIp;
import com.linzeming.proxypool.crawler.task.anonymitycheck.AnonymityCheckQueueConsumer;
import com.linzeming.proxypool.crawler.task.validateproxy.ValidateProxyIpQueueConsumer;
import com.linzeming.proxypool.crawler.task.validateproxy.ValidateProxyIpQueueProducer;
import com.linzeming.proxypool.crawler.util.Constants;
import com.linzeming.proxypool.crawler.util.ProxyUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;



@EnableScheduling
public class Starter {
    public static void main(String[] args) throws Exception {


        // 初始化ioc容器
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(ContextMainConfig.class);
        final Logger logger = LoggerFactory.getLogger(Starter.class);
        logger.info("Proxy pool init...");
        //初始化 AnonymityCheckQueueConsumer 线程
        logger.info("Create {} Thread for AnonymityCheckQueueConsumer",Constants.AnonymityCheckQueueConsumerThreadCount);
        ThreadPoolTaskExecutor anonymityCheckQueueConsumerExecutor = (ThreadPoolTaskExecutor) applicationContext.getBean("anonymityCheckQueueConsumerExecutor");
        for (int i = 0; i <  Constants.AnonymityCheckQueueConsumerThreadCount; i++) {
            anonymityCheckQueueConsumerExecutor.execute(new Thread(new AnonymityCheckQueueConsumer()));
        }

        // 初始化ValidateProxyIpQueueProducer线程 1条
        logger.info("Create 1 Thread for ValidateProxyIpQueueProducer");
        new Thread(new ValidateProxyIpQueueProducer()).start();

        //初始化 ValidateProxyIpQueueConsumer
        logger.info("Create {} Thread for ValidateProxyIpQueueConsumer",Constants.ValidateProxyIpQueueConsumerThreadCount);
        ThreadPoolTaskExecutor validateProxyIpQueueConsumerExecutor = (ThreadPoolTaskExecutor) applicationContext.getBean("validateProxyIpQueueConsumerExecutor");
        for (int i = 0; i < Constants.ValidateProxyIpQueueConsumerThreadCount; i++) {
            validateProxyIpQueueConsumerExecutor.execute(new Thread(new ValidateProxyIpQueueConsumer()));
        }
    }
}
