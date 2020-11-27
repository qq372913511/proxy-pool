package com.linzeming.proxypool.crawler.task.validateproxy;

import com.linzeming.proxypool.crawler.dao.ProxyIpDao;
import com.linzeming.proxypool.crawler.entity.ProxyIp;
import com.linzeming.proxypool.crawler.util.Constants;
import com.linzeming.proxypool.crawler.util.ProxyUtils;
import com.linzeming.proxypool.crawler.util.SpringUtil;
import org.springframework.stereotype.Component;


/**
 * 从proxypool:validateProxiesQueue队列中提取出来需要验证的proxy，然后进行验证打分
 */


public class ValidateProxyIpQueueConsumer implements Runnable {
    ProxyIpDao proxyIpDao = (ProxyIpDao) SpringUtil.getBean("proxyIpDao");

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
                }
            }
            //非null
            proxyIpDao.setValidateTime(proxyIp, System.currentTimeMillis());
            int i = ProxyUtils.validateConnectivity(proxyIp);
            if (i == 0) {
                //等于0是成功 有效的
                proxyIpDao.setValidatedProxyScore(proxyIp, Constants.validatedProxyMaxScore);
                System.out.println("重新验证代理结果: " + proxyIp + " - " + Constants.validatedProxyMaxScore);
            } else {
                //其他无效
                proxyIpDao.decreaseValidatedProxyScore(proxyIp, 1.0);
                System.out.println("重新验证代理结果: " + proxyIp + " - " + "decreaseValidatedProxyScore 1.0");
            }
        }
    }
}
