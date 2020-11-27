package com.linzeming.proxypool.crawler.task.crawler;

import com.linzeming.proxypool.crawler.dao.ProxyIpDao;
import com.linzeming.proxypool.crawler.entity.ProxyIp;
import com.linzeming.proxypool.crawler.parser.Parser;
import com.linzeming.proxypool.crawler.util.Constants;
import com.linzeming.proxypool.crawler.util.SpringUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.Map;


/**
 * 为每个parser添加crawler的trigger任务到scheduler
 */
@Component
public class CrawlerScheduledTaskCreator {
    @Autowired
    ProxyIpDao proxyIpDao;
    Logger logger = LoggerFactory.getLogger(CrawlerScheduledTaskCreator.class);

    @Bean
    public ScheduledTaskRegistrar scheduledTaskRegistrar() {
        ScheduledTaskRegistrar scheduledTaskRegistrar = new ScheduledTaskRegistrar();
        //先找所有的parser
        Map<String, Parser> beansOfTypes = SpringUtil.getBeansOfTypes(Parser.class);
        for (String parserBeanName : beansOfTypes.keySet()) {
            // 获取parser
            Parser parser = beansOfTypes.get(parserBeanName);
            if (!parser.isEnable()) {
                continue;
            }
            System.out.println("[Load parser] " + parserBeanName);
            Runnable runnable = () -> {
                // get context
                ProxyIpDao proxyDao = (ProxyIpDao) SpringUtil.getBean("proxyIpDao");
                // parser干活
                List<String> urls = parser.getUrls();
                for (String url : urls) {
                    Document document = null;
                    try {
                        document = Jsoup.connect(url).get();
                    } catch (IOException e) {
                        logger.warn("[{}] " + "Jsoup Connection Error: {}", parserBeanName, e);
                        continue;
                    }
                    List<ProxyIp> proxies = null;
                    try {
                        proxies = parser.parseDocument(document);
                    } catch (Exception e) {
                        logger.warn("[{}] " + "Jsoup parseDocument Error: {}", parserBeanName, e);
                        continue;
                    }
                    //加到透明性验证队列里面
                    for (ProxyIp proxyIp : proxies) {
                        //todo pieline优化
                        logger.info("[{}] " + "Add into NX Proxies List: {}", parserBeanName, proxyIp.toString());
                        proxyIp.setScore(Constants.validatedProxyInitScore);
                        proxyDao.addIntoProxiesZsetNx(proxyIp);
                    }
                }
            };

            Trigger trigger = triggerContext -> {
                String cron = parser.getCron();
                CronTrigger cronTrigger = new CronTrigger(cron);
                return cronTrigger.nextExecutionTime(triggerContext);
            };
            scheduledTaskRegistrar.addTriggerTask(runnable, trigger);
        }
        return scheduledTaskRegistrar;
    }
}
