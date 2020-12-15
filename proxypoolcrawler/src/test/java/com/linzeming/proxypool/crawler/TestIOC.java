package com.linzeming.proxypool.crawler;
import static org.springframework.data.mongodb.core.query.Criteria.where;
import com.alibaba.druid.pool.DruidDataSource;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.linzeming.proxypool.crawler.dao.ProxyIpValidateLogResultMongodbDao;
import com.linzeming.proxypool.crawler.mapper.ProxyIpMapper;
import com.linzeming.proxypool.crawler.model.ProxyIp;
import com.linzeming.proxypool.crawler.model.ProxyIpValidateLogResult;
import com.linzeming.proxypool.crawler.service.ProxyIpService;
import com.linzeming.proxypool.crawler.util.SpringUtil;
import org.junit.Test;
import java.util.Random;

import com.linzeming.proxypool.crawler.config.ContextMainConfig;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;


public class TestIOC {
    private final ApplicationContext ioc = new AnnotationConfigApplicationContext(ContextMainConfig.class);


    @Test
    public void testDataSource() throws SQLException {
        DataSource dataSource = ioc.getBean("dataSource", DataSource.class);
        System.out.println(dataSource);
        Connection connection = dataSource.getConnection();
        System.out.println(connection);
    }

    @Test
    public void testDataSourceSame() {
        DataSourceTransactionManager dataSourceTransactionManager = ioc.getBean("dataSourceTransactionManager", DataSourceTransactionManager.class);
        System.out.println(dataSourceTransactionManager.getDataSource());

        DruidDataSource druidDataSource = ioc.getBean("dataSource", DruidDataSource.class);
        System.out.println(druidDataSource);
        System.out.println(druidDataSource.equals(dataSourceTransactionManager.getDataSource()));
    }

    @Test
    public void testService() throws SQLException {
        ProxyIpService proxyIpServiceImpl = ioc.getBean("proxyIpServiceImpl", ProxyIpService.class);
        System.out.println(proxyIpServiceImpl.save(new ProxyIp("232.232.232.232", 23405)));

    }

    @Test
    public void testDao() throws SQLException {
        ProxyIpMapper proxyIpMapper = ioc.getBean("proxyIpMapper", ProxyIpMapper.class);
        System.out.println(proxyIpMapper.insert(new ProxyIp("232.232.232.231", 23405)));
    }

    @Test
    public void testUtilGetService() {
        ProxyIpService proxyIpService = (ProxyIpService) SpringUtil.getBean("proxyIpServiceImpl");
        proxyIpService.save(new ProxyIp("232.232.232.221", 23405));
    }

    @Test
    public void testMapper() throws SQLException {
        ProxyIpMapper proxyIpMapper = ioc.getBean("proxyIpMapper", ProxyIpMapper.class);
        System.out.println(proxyIpMapper.insertNewProxyIpIgnoreInto(new ProxyIp("232.232.232.231", 23405)));
    }

    @Test
    public void testAllSelect() {
        ProxyIpMapper proxyIpMapper = ioc.getBean("proxyIpMapper", ProxyIpMapper.class);
        QueryWrapper<ProxyIp> proxyIpQueryWrapper = new QueryWrapper<>();
        proxyIpQueryWrapper.select("id", "ip", "port");

//        Page<ProxyIp> proxyIpPage = proxyIpMapper.selectPage(new Page<>(2,10), proxyIpQueryWrapper);
//        System.out.println(proxyIpPage.getCountId());
//        System.out.println(proxyIpPage.getCurrent());
//        System.out.println(proxyIpPage.getPages());
//        System.out.println(proxyIpPage.getTotal());
//        System.out.println(proxyIpPage.getSize());
//        System.out.println(proxyIpPage.getRecords());
        List<ProxyIp> proxyIps = proxyIpMapper.selectList(proxyIpQueryWrapper);
        System.out.println(proxyIps.get(1));

    }

    @Test
    public void testUpdateWrapper() {
        ProxyIpMapper proxyIpMapper = ioc.getBean("proxyIpMapper", ProxyIpMapper.class);
        UpdateWrapper<ProxyIp> proxyIpUpdateWrapper = new UpdateWrapper<>();
        proxyIpUpdateWrapper.eq("id", 1548324);
        proxyIpUpdateWrapper.set("is_https",1);
        proxyIpMapper.update(new ProxyIp("123.123.123.123", 12312), proxyIpUpdateWrapper);
    }


    @Test
    public void testMongodb(){
        Random random = new Random();

        for (int i = 0; i < 10_00000; i++) {
            ProxyIpValidateLogResultMongodbDao dao = ioc.getBean("proxyIpValidateLogResultMongodbDao", ProxyIpValidateLogResultMongodbDao.class);
            ProxyIpValidateLogResult proxyIpValidateLogResult = new ProxyIpValidateLogResult();
            proxyIpValidateLogResult.setConnectionSpeed(random.nextInt());
            proxyIpValidateLogResult.setGmtLastValidate(LocalDateTime.now());
            proxyIpValidateLogResult.setProxyIpId(random.nextInt());
            dao.insertProxyIpValidateLogResult(proxyIpValidateLogResult);
            System.out.println(proxyIpValidateLogResult);
        }

//        System.out.println(mongoTemplate.findOne(new Query(where("name").is("Joe")), MongodbTestModel.class));
//        mongoTemplate.dropCollection("proxyIp");
    }


}
