package com.linzeming.proxypool.crawler.config;


import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({TaskSchedulerConfig.class, ExecutorConfig.class,RedisConfig.class,DataSourceConfig.class})
@ComponentScan(basePackages = "com.linzeming.proxypool.crawler.*")
public class ContextMainConfig {


}
