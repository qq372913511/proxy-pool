package com.linzeming.proxypool.crawler;

import com.alibaba.druid.pool.DruidDataSource;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.VelocityTemplateEngine;
import com.linzeming.proxypool.crawler.config.ContextMainConfig;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class MPGenerator {
    private final ApplicationContext ioc = new AnnotationConfigApplicationContext(ContextMainConfig.class);
//    private final ProxyIpMapper proxyIpMapper = ioc.getBean("proxyIpMapper", ProxyIpMapper.class);


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
    public void mpGenerator(){
        // 代码生成器
        AutoGenerator autoGenerator = new AutoGenerator();

        GlobalConfig globalConfig = new GlobalConfig();
        globalConfig.setOutputDir("D:/Nextcloud_Linzeming/Program Project/Java/proxy-pool/proxypoolcrawler/src/main/java");
        globalConfig.setFileOverride(true);
        globalConfig.setEnableCache(true);
        globalConfig.setAuthor("linzeming");
        globalConfig.setBaseResultMap(true);
        globalConfig.setBaseColumnList(true);
        globalConfig.setServiceName("%sService");
        autoGenerator.setGlobalConfig(globalConfig);

        DataSourceConfig dataSourceConfig = new DataSourceConfig();
        dataSourceConfig.setUrl("jdbc:mysql://10.0.0.88:3306/proxypool?useUnicode=true&useSSL=false&characterEncoding=utf8");
        dataSourceConfig.setDriverName("com.mysql.jdbc.Driver");
        dataSourceConfig.setUsername("javadev");
        dataSourceConfig.setPassword("javadev");
        autoGenerator.setDataSource(dataSourceConfig);


        // 包配置
        PackageConfig packageConfig = new PackageConfig();
        packageConfig.setParent("com.linzeming.proxypool.crawler.template");
        packageConfig.setController("controller");
        packageConfig.setService("service");
        packageConfig.setEntity("model");
        packageConfig.setMapper("mapper");
        packageConfig.setXml("mapper");
        packageConfig.setServiceImpl("service.impl");
        autoGenerator.setPackageInfo(packageConfig);

        // 策略配置
        StrategyConfig strategyConfig = new StrategyConfig();
        strategyConfig.setNaming(NamingStrategy.underline_to_camel);
        strategyConfig.setColumnNaming(NamingStrategy.underline_to_camel);
        // strategyConfig.setSuperEntityClass("com.baomidou.ant.common.BaseEntity");
        strategyConfig.setEntityLombokModel(false);
        strategyConfig.setRestControllerStyle(true);
        // strategyConfig.setSuperControllerClass("com.baomidou.ant.common.BaseController");

        //自动将数据库中表名为 user_info 格式的专为 UserInfo 命名
        strategyConfig.setTablePrefix(packageConfig.getModuleName() + "_");
        autoGenerator.setStrategy(strategyConfig);



        autoGenerator.setTemplateEngine(new VelocityTemplateEngine());


        System.out.println("===================== MyBatis Plus Generator ==================");

        autoGenerator.execute();

        System.out.println("================= MyBatis Plus Generator Execute Complete ==================");
    }

}
