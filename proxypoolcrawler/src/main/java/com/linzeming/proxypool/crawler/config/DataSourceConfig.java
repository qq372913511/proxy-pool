package com.linzeming.proxypool.crawler.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.core.MybatisConfiguration;
import com.baomidou.mybatisplus.core.config.GlobalConfig;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import org.apache.ibatis.plugin.Interceptor;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.annotation.Resource;

@Configuration
@EnableTransactionManagement
public class DataSourceConfig {
    @Bean
    DruidDataSource dataSource() {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource.setUsername("javadev");
        dataSource.setPassword("javadev");
        dataSource.setUrl("jdbc:mysql://10.0.0.88:3306/proxypool?rewriteBatchedStatements=true");
        dataSource.setMaxActive(8);
        dataSource.setMaxWait(5000);
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
    public MybatisPlusInterceptor mybatisPlusInterceptor(){
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
        return interceptor;
    }

    @Bean
    MybatisSqlSessionFactoryBean mybatisSqlSessionFactoryBean(){
        MybatisSqlSessionFactoryBean mybatisSqlSessionFactoryBean = new MybatisSqlSessionFactoryBean();
        mybatisSqlSessionFactoryBean.setDataSource(dataSource()); // todo
        mybatisSqlSessionFactoryBean.setConfiguration(mybatisConfiguration());
        Interceptor[] plugins = {mybatisPlusInterceptor()};
        mybatisSqlSessionFactoryBean.setPlugins(plugins);
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
        mapperScannerConfigurer.setSqlSessionFactoryBeanName("mybatisSqlSessionFactoryBean");
        mapperScannerConfigurer.setBasePackage("com.linzeming.proxypool.crawler.mapper");
        return mapperScannerConfigurer;
    }






}
