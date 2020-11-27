package com.linzeming.proxypool.crawler.task.validate;


import com.linzeming.proxypool.crawler.dao.ProxyIpDao;
import com.linzeming.proxypool.crawler.entity.ProxyIp;
import com.linzeming.proxypool.crawler.util.Constants;
import com.linzeming.proxypool.crawler.util.ProxyUtils;
import com.linzeming.proxypool.crawler.util.SpringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Set;

/**
 * 从redis里面挑出过期的ip，加入阻塞队列
 */
public class ValidateProxyIpQueueProducer implements Runnable {
    ProxyIpDao proxyIpDao = (ProxyIpDao) SpringUtil.getBean("proxyIpDao");

    Logger logger = LoggerFactory.getLogger(ValidateProxyIpQueueProducer.class);


    /**
     * 死循环
     * down下来的差值对每个进行遍历
     * 过滤掉上次检查时间还较短的
     * 复合要求的加入到验证队列中
     */
    @Override
    public void run() {
        while (true) {
            // 检查是否超过阈值 超过的话等待下一次
            if (proxyIpDao.countValidateProxiesQueue() > Constants.queueThreshold) {
                try {
                    Thread.sleep(Constants.refreshInterval);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                continue;
            }

            // 低于阈值，需要对队列进行补充 首先获取所有的proxy 对10次连续验证失败的放弃掉
            Set<String> rangeValidatedProxies = proxyIpDao.getRangeProxiesByScore(Constants.insertIntoValidateQueueMinScore, Constants.insertIntoValidateQueueMaxScore);
            List<ProxyIp> proxyIps = ProxyUtils.convertProxysStringSetToList(rangeValidatedProxies);
            proxyIpDao.addIntoValidateProxiesQueueMany(proxyIps);
            logger.info("add proxies: {}", proxyIps.size());
        }
    }
}
