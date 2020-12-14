package com.linzeming.proxypool.crawler.task.validate;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.linzeming.proxypool.crawler.dao.ProxyIpRedisDao;
import com.linzeming.proxypool.crawler.mapper.ProxyIpMapper;
import com.linzeming.proxypool.crawler.model.ProxyIp;
import com.linzeming.proxypool.crawler.model.ProxyIpValidateLogResult;
import com.linzeming.proxypool.crawler.util.Constants;
import com.linzeming.proxypool.crawler.util.ProxyUtils;
import com.linzeming.proxypool.crawler.util.SpringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * 从proxypool:validateProxiesQueue队列中提取出来需要验证的proxy，然后进行验证打分
 */


public class ValidateProxyIpQueueConsumer implements Runnable {
    ProxyIpRedisDao proxyIpRedisDao = (ProxyIpRedisDao) SpringUtil.getBean("proxyIpRedisDao");
    ProxyIpMapper proxyIpMapper = (ProxyIpMapper) SpringUtil.getBean("proxyIpMapper");
    Logger logger = LoggerFactory.getLogger(ValidateProxyIpQueueConsumer.class);

    @Override
    public void run() {
        while (true) {
            try {
                ProxyIp proxyIp = proxyIpRedisDao.takeProxyFromValidateQueue();
                // 判null
                if (proxyIp == null) {
                    Thread.sleep(1000);
                    continue;
                }
                ;
                // 非null
                ProxyUtils.validateProxy(proxyIp);
                // 结果写到数据库
                proxyIpMapper.updateById(proxyIp);
                logger.info("updateConnectionSpeedAndAnonymity: {}", proxyIp);
                //验证结果记录到redis
                ProxyIpValidateLogResult proxyIpValidateLogResult = new ProxyIpValidateLogResult(proxyIp.formatIpPort(),
                        proxyIp.getGmtLastValidate().toString(),
                        proxyIp.getConnectionSpeed().toString());
                proxyIpRedisDao.logValidateResult(proxyIpValidateLogResult);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }
}
