package com.linzeming.proxypool.crawler;

import com.alibaba.druid.pool.DruidDataSource;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import com.linzeming.proxypool.crawler.mapper.ProxyIpMapper;
import com.linzeming.proxypool.crawler.model.ProxyIp;
import org.junit.Test;

import com.linzeming.proxypool.crawler.config.ContextMainConfig;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class TestIOC {
    private final ApplicationContext ioc = new AnnotationConfigApplicationContext(ContextMainConfig.class);
    private final ProxyIpMapper proxyIpMapper = ioc.getBean("proxyIpMapper", ProxyIpMapper.class);


    @Test
    public void testDataSource() throws SQLException {
        DataSource dataSource = ioc.getBean("dataSource", DataSource.class);
        System.out.println(dataSource);
        Connection connection = dataSource.getConnection();
        System.out.println(connection);
    }

    @Test
    public void testDataSourceSame (){
        DataSourceTransactionManager dataSourceTransactionManager = ioc.getBean("dataSourceTransactionManager", DataSourceTransactionManager.class);
        System.out.println(dataSourceTransactionManager.getDataSource());

        DruidDataSource druidDataSource = ioc.getBean("dataSource", DruidDataSource.class);
        System.out.println(druidDataSource);
        System.out.println(druidDataSource.equals(dataSourceTransactionManager.getDataSource()));
    }

    @Test
    public void testMybatisPlus(){
        ProxyIp proxyIp = new ProxyIp();
        proxyIp.setIp("123.2.1.2");
        proxyIp.setPort(123);
        proxyIpMapper.insert(proxyIp);
    }

}
