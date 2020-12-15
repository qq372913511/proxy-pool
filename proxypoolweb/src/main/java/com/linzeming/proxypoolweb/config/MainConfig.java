package com.linzeming.proxypoolweb.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.core.MybatisConfiguration;
import com.baomidou.mybatisplus.core.config.GlobalConfig;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import com.linzeming.proxypoolweb.util.Constants;
import org.apache.ibatis.plugin.Interceptor;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.convert.DefaultDbRefResolver;
import org.springframework.data.mongodb.core.convert.DefaultMongoTypeMapper;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

@Configuration
@ComponentScan(basePackages = "com.linzeming.proxypoolweb.*")
public class MainConfig {

    /**
     * redis配置
     * @return
     */
    @Bean
    public JedisPool jedisPool() {
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        //控制一个pool可分配多少个jedis实例
        jedisPoolConfig.setMaxTotal(20);
        //控制一个pool最多有多少个状态为idle(空闲)的jedis实例
        jedisPoolConfig.setMaxIdle(10);
        //表示当borrow一个jedis实例时，最大的等待时间，如果超过等待时间，则直接抛出JedisConnectionException
        jedisPoolConfig.setMaxWaitMillis(5000);
        //在borrow一个jedis实例时，是否提前进行validate操作；如果为true，则得到的jedis实例均是可用的
        jedisPoolConfig.setTestOnBorrow(true);
        JedisPool jedisPool = new JedisPool(jedisPoolConfig, "10.0.0.88", 6379);
        return jedisPool;
    }

    /**
     * 数据库连接池配置, mybatis配置
     * @return
     */
    @Bean
    public DruidDataSource dataSource() {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource.setUsername("javadev");
        dataSource.setPassword("javadev");
        dataSource.setUrl("jdbc:mysql://10.0.0.88:3306/proxypool?rewriteBatchedStatements=true");
        dataSource.setMaxActive(Constants.dataSourceMaxActive);
        dataSource.setMaxWait(Constants.dataSourceMaxWait);
        return dataSource;
    }

    @Bean
    public DataSourceTransactionManager dataSourceTransactionManager(){
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
    public MybatisSqlSessionFactoryBean mybatisSqlSessionFactoryBean(){
        MybatisSqlSessionFactoryBean mybatisSqlSessionFactoryBean = new MybatisSqlSessionFactoryBean();
        mybatisSqlSessionFactoryBean.setDataSource(dataSource()); // todo
        mybatisSqlSessionFactoryBean.setConfiguration(mybatisConfiguration());
        Interceptor[] plugins = {mybatisPlusInterceptor()};
        mybatisSqlSessionFactoryBean.setPlugins(plugins);
//        mybatisSqlSessionFactoryBean.setTypeAliasesPackage();
        return mybatisSqlSessionFactoryBean;
    }

    @Bean
    public MybatisConfiguration mybatisConfiguration(){
        MybatisConfiguration mybatisConfiguration = new MybatisConfiguration();
        mybatisConfiguration.setMapUnderscoreToCamelCase(true);
        return mybatisConfiguration;
    }
    @Bean
    public GlobalConfig globalConfig(){
        GlobalConfig globalConfig = new GlobalConfig();
        return globalConfig;
    }
    @Bean
    public GlobalConfig.DbConfig dbConfig(){
        GlobalConfig.DbConfig dbConfig = new GlobalConfig.DbConfig();
        return dbConfig;
    }

    @Bean
    public MapperScannerConfigurer mapperScannerConfigurer(){
        MapperScannerConfigurer mapperScannerConfigurer = new MapperScannerConfigurer();
        mapperScannerConfigurer.setSqlSessionFactoryBeanName("mybatisSqlSessionFactoryBean");
        mapperScannerConfigurer.setBasePackage("com.linzeming.proxypoolweb.mapper");
        return mapperScannerConfigurer;
    }


//    @Bean
//    @ConditionalOnMissingBean
//    public InternalResourceViewResolver defaultViewResolver() {
//        InternalResourceViewResolver resolver = new InternalResourceViewResolver();
//        resolver.setPrefix("/templates");
//        resolver.setSuffix(".html");
////        resolver.setPrefix(this.mvcProperties.getView().getPrefix());
////        resolver.setSuffix(this.mvcProperties.getView().getSuffix());
//        return resolver;
//    }


//    /**
//     * MongoDB 配置
//     */
//    @Bean
//    public MongoDbFactory mongoDbFactory() throws Exception {
//        MongoClientURI uri = new MongoClientURI("mongodb://10.0.0.88:27017/proxy_pool?maxPoolSize=20&waitQueueMultiple=20");
//        SimpleMongoDbFactory simpleMongoDbFactory = new SimpleMongoDbFactory(uri);
//        return simpleMongoDbFactory;
//    }


//    @Bean
//    public MongoTemplate mongoTemplate() throws Exception {
//        //remove _class
//        MappingMongoConverter converter = new MappingMongoConverter(new DefaultDbRefResolver(mongoDbFactory()), new MongoMappingContext());
//        converter.setTypeMapper(new DefaultMongoTypeMapper(null));
//        converter.afterPropertiesSet();
//        return new MongoTemplate(mongoDbFactory(), converter);
//    }
}