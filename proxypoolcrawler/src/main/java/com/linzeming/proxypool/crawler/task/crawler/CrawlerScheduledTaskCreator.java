package com.linzeming.proxypool.crawler.task.crawler;

import com.linzeming.proxypool.crawler.model.ProxyIp;
import com.linzeming.proxypool.crawler.parser.Parser;
import com.linzeming.proxypool.crawler.service.ProxyIpService;
import com.linzeming.proxypool.crawler.util.Geo;
import com.linzeming.proxypool.crawler.util.ProxyUtils;
import com.linzeming.proxypool.crawler.util.SpringUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
            logger.info("[Load parser] " + parserBeanName);
            Runnable runnable = () -> {
                // get context
                ProxyIpService proxyIpService = (ProxyIpService) SpringUtil.getBean("proxyIpServiceImpl");
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

                    //如果proxy长度为0则直接跳过
                    if (proxies.size() == 0) continue;

                    // 对proxy进行处理
                    for (ProxyIp proxyIp : proxies) {
                        //对ip进行格式化
                        proxyIp.setIp(ProxyUtils.formatIp(proxyIp.getIp()));
                        //补充地理信息
                        try {
                            Geo.completeProxyIpGeoInfo(proxyIp);
                        } catch (Exception e) {
                            e.printStackTrace();
                            proxyIp.setCountry("");
                            proxyIp.setCity("");
                        }
                    }

                    // 验证proxies的长度
                    if (proxies.size() > 0) {
                        //加到透明性验证队列里面
                        proxyIpService.insertProxyIpIgnoreIntoBatch(proxies);
                        //                    proxyIpService.saveBatch(proxies, 1000);
//                    for (ProxyIp proxyIp : proxies) {
//                        try {
//                            proxyIpService.save(proxyIp);
//                            logger.info("[{}] " + "Add into NX Proxies List: {}", parserBeanName, proxyIp.toString());
//                        } catch (Exception e) {
//                        }
//                    }
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
