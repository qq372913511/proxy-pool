package com.linzeming.proxypool.crawler.task.validateproxy;


import com.linzeming.proxypool.crawler.dao.ProxyIpDao;
import com.linzeming.proxypool.crawler.entity.ProxyIp;
import com.linzeming.proxypool.crawler.util.Constants;
import com.linzeming.proxypool.crawler.util.ProxyUtils;
import com.linzeming.proxypool.crawler.util.SpringUtil;

import java.util.Set;

/**
 * 从redis里面挑出过期的ip，加入阻塞队列
 */
public class ValidateProxyIpQueueProducer implements Runnable {
    ProxyIpDao proxyIpDao = (ProxyIpDao) SpringUtil.getBean("proxyIpDao");
    Integer proxyExpireSecond = 5 * 60; // proxy的过期时间, 上次验证时间离现在超过这个时长则拉下来验证。
    Integer queueThreshold = 30; // 队列size低于这个阈值则开始从redis中获取过期的proxy
    Integer refreshInterval = 5 * 1000; // 每几秒运行一次


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
            if (proxyIpDao.countValidateProxiesQueue() > queueThreshold) {
                try {
                    Thread.sleep(refreshInterval);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                continue;
            }

            // 低于阈值，需要对队列进行补充 首先获取所有的proxy
            Set<String> rangeValidatedProxies = proxyIpDao.getRangeValidatedProxiesByScore(Constants.validatedProxyMinScore + 1, Constants.validatedProxyMaxScore);
            for (String proxyString : rangeValidatedProxies) {
                //转换
                ProxyIp proxyIp = ProxyUtils.convertProxy(proxyString);
                //过滤在队列里的
                if (proxyIpDao.existsInValidateProxiesQueue(proxyIp)) {
                    continue;
                }
                //过滤上次检查的时间
                long lastCheckTime = proxyIpDao.getValidateTime(proxyIp);
                if (System.currentTimeMillis() - lastCheckTime < proxyExpireSecond * 1000) {
                    continue;
                }
                //符合条件，添加到队列中
                System.out.println("添加proxy到验证队列中: " + proxyIp.toString());
                proxyIpDao.addIntoValidateProxiesQueue(proxyIp);
            }
        }
    }
}
