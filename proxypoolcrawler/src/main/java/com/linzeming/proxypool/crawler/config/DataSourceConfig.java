package com.linzeming.proxypool.crawler.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.baomidou.mybatisplus.core.MybatisConfiguration;
import com.baomidou.mybatisplus.core.config.GlobalConfig;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.annotation.Resource;

@Configuration
public class DataSourceConfig {
    @Bean
    DruidDataSource dataSource() {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource.setUsername("linzeming");
        dataSource.setPassword("02030136");
        dataSource.setUrl("jdbc:mysql://10.0.0.88:3306/proxypool");
        /*
            jdbc.driver=com.mysql.cj.jdbc.Driver
            jdbc.url=jdbc:mysql://10.0.0.88:3306/proxypool
            jdbc.username=linzeming
            jdbc.password="02030136"
         */
        return dataSource;
    }

    @Bean
    DataSourceTransactionManager dataSourceTransactionManager(){
        DataSourceTransactionManager dataSourceTransactionManager = new DataSourceTransactionManager();
        dataSourceTransactionManager.setDataSource(dataSource()); // todo
        return dataSourceTransactionManager;
    }

    /*
    mybatis plus配置
     */

    @Bean
    MybatisSqlSessionFactoryBean mybatisSqlSessionFactoryBean(){
        MybatisSqlSessionFactoryBean mybatisSqlSessionFactoryBean = new MybatisSqlSessionFactoryBean();
        mybatisSqlSessionFactoryBean.setDataSource(dataSource()); // todo
        mybatisSqlSessionFactoryBean.setConfiguration(mybatisConfiguration());
//        mybatisSqlSessionFactoryBean.setTypeAliasesPackage();
        return mybatisSqlSessionFactoryBean;
    }

    @Bean
    MybatisConfiguration mybatisConfiguration(){
        MybatisConfiguration mybatisConfiguration = new MybatisConfiguration();
        mybatisConfiguration.setMapUnderscoreToCamelCase(true);

        return mybatisConfiguration;
    }
    @Bean
    GlobalConfig globalConfig(){
        GlobalConfig globalConfig = new GlobalConfig();
        return globalConfig;
    }
    @Bean
    GlobalConfig.DbConfig dbConfig(){
        GlobalConfig.DbConfig dbConfig = new GlobalConfig.DbConfig();
        return dbConfig;
    }

    @Bean
    MapperScannerConfigurer mapperScannerConfigurer(){
        MapperScannerConfigurer mapperScannerConfigurer = new MapperScannerConfigurer();
        mapperScannerConfigurer.setBasePackage("com.linzeming.proxypool.crawler.mapper");
        return mapperScannerConfigurer;
    }
}
