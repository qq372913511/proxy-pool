package com.linzeming.proxypool.crawler.task.validate;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.linzeming.proxypool.crawler.dao.ProxyIpRedisDao;
import com.linzeming.proxypool.crawler.mapper.ProxyIpMapper;
import com.linzeming.proxypool.crawler.model.ProxyIp;
import com.linzeming.proxypool.crawler.util.Constants;
import com.linzeming.proxypool.crawler.util.ProxyUtils;
import com.linzeming.proxypool.crawler.util.SpringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Set;

/**
 * 从数据库挑出ip，加入到队列中
 */
public class ValidateProxyIpQueueProducer implements Runnable {
    Logger logger = LoggerFactory.getLogger(ValidateProxyIpQueueProducer.class);

    ProxyIpRedisDao proxyIpRedisDao = (ProxyIpRedisDao) SpringUtil.getBean("proxyIpRedisDao");
    ProxyIpMapper proxyIpMapper = (ProxyIpMapper) SpringUtil.getBean("proxyIpMapper");



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
//            if (proxyIpRedisDao.countValidateProxiesQueue() > Constants.queueThreshold) {
//                try {
//                    Thread.sleep(Constants.refreshInterval);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//                continue;
//            }

            // 低于阈值，需要对队列进行补充 首先获取所有的proxy 对10次连续验证失败的放弃掉
            try {
                QueryWrapper<ProxyIp> proxyIpQueryWrapper = new QueryWrapper<>();
                proxyIpQueryWrapper.select("id","ip","port");
                List<ProxyIp> proxyIps = proxyIpMapper.selectList(proxyIpQueryWrapper);
                proxyIpRedisDao.insertProxyIpIntoValidateQueue(proxyIps);
                logger.info("add validate proxyIps: {}", proxyIps.size());
                //interval
                Thread.sleep(Constants.validateProxyIpInterval);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
