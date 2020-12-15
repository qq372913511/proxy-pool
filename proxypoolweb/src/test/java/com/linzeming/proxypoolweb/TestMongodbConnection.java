package com.linzeming.proxypoolweb;

import com.linzeming.proxypoolweb.dao.ProxyIpValidateLogResultDao;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.time.LocalDateTime;


@RunWith(SpringRunner.class)
@SpringBootTest
public class TestMongodbConnection {
    @Autowired
    private ProxyIpValidateLogResultDao proxyIpValidateLogResultDao;

    @Test
    public void test1(){
        System.out.println(proxyIpValidateLogResultDao.findByProxyIpIdAndGmtLastValidateGreaterThanOrderByGmtLastValidateAsc(2033389, LocalDateTime.now()));


    }
}
