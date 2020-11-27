package com.linzeming.proxypool.crawler.task.validate;

import com.linzeming.proxypool.crawler.dao.ProxyIpDao;
import com.linzeming.proxypool.crawler.entity.ProxyIp;
import com.linzeming.proxypool.crawler.util.Constants;
import com.linzeming.proxypool.crawler.util.ProxyUtils;
import com.linzeming.proxypool.crawler.util.SpringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * 从proxypool:validateProxiesQueue队列中提取出来需要验证的proxy，然后进行验证打分
 */


public class ValidateProxyIpQueueConsumer implements Runnable {
    ProxyIpDao proxyIpDao = (ProxyIpDao) SpringUtil.getBean("proxyIpDao");
    Logger logger  = LoggerFactory.getLogger(ValidateProxyIpQueueConsumer.class);

    @Override
    public void run() {
        while (true) {
            // 获取一个proxy 获取同时set validatetime todo 本质上这里应该原子的，之后可以用lua脚本实现
            ProxyIp proxyIp = proxyIpDao.takeFromValidateProxiesQueue();
            // 判null
            if (proxyIp == null) {
                try {
                    Thread.sleep(1000);
                    continue;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    continue;
                }
            }
            //非null
            proxyIp.setValidateTimestamp(System.currentTimeMillis());
            proxyIpDao.setLastValidateTime(proxyIp);
            boolean i = ProxyUtils.validateProxy(proxyIp);
            if (i) {
                //等于0是成功 有效的
                proxyIp.setScore(Constants.validatedProxyMaxScore);
                proxyIpDao.setProxyScore(proxyIp);
                proxyIp.setLastValidateResult(true);
                proxyIpDao.addValidatePorxyResult(proxyIp);
                logger.info("重新验证代理结果: " + proxyIp + " - " + Constants.validatedProxyMaxScore);
            } else {
                //其他无效
                proxyIpDao.decreaseProxyScore(proxyIp, 1.0);
                proxyIp.setLastValidateResult(false);
                proxyIpDao.addValidatePorxyResult(proxyIp);
                logger.info("重新验证代理结果: " + proxyIp + " - " + "decreaseValidatedProxyScore 1.0");
            }
        }
    }
}
