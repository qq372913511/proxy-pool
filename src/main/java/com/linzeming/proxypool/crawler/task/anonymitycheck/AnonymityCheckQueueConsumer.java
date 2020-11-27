package com.linzeming.proxypool.crawler.task.anonymitycheck;


import com.linzeming.proxypool.crawler.dao.ProxyIpDao;
import com.linzeming.proxypool.crawler.entity.ProxyIp;
import com.linzeming.proxypool.crawler.util.Constants;
import com.linzeming.proxypool.crawler.util.ProxyUtils;
import com.linzeming.proxypool.crawler.util.SpringUtil;

/**
 * 负责从验证队列里面提取proxy然后进行验证 守护线程
 */
public class AnonymityCheckQueueConsumer implements Runnable {
    ProxyIpDao proxyIpDao = (ProxyIpDao) SpringUtil.getBean("proxyIpDao");

    @Override
    public void run() {
        while (true) {
            //获取一个proxyIP
            ProxyIp proxyIp = proxyIpDao.takeFromAnonymityCheckQueue();
            //判null
            if (proxyIp == null) {
                try {
                    Thread.sleep(1000);
                    continue;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            //非null
            System.out.println("提取到需要进行验证匿名性的proxy: " + proxyIp.getFormattedIpPort());
            //验证透明性
            boolean anonymity = ProxyUtils.validateAnonymity(proxyIp);
            System.out.println(proxyIp.getFormattedIpPort() + ": 匿名性 - " + anonymity);
            //根据透明性结果做出相应的操作
            if (anonymity) {
                // 插入到被验证的代理 带一个初始分数
                proxyIp.setScore(Constants.validatedProxyInitScore);
                proxyIpDao.addIntoValidatedProxies(proxyIp);
                proxyIpDao.setValidateTime(proxyIp,System.currentTimeMillis());
            } else {
                // 插入到黑名单
                proxyIpDao.addIntoBlackSet(proxyIp);
                proxyIpDao.setValidateTime(proxyIp,System.currentTimeMillis());
            }


        }
    }
}
