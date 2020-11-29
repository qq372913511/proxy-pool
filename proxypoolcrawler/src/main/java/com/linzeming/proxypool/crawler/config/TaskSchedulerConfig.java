package com.linzeming.proxypool.crawler.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;


/**
 * 初始化定时任务线程池
 */

@Configuration
@EnableScheduling
public class TaskSchedulerConfig implements SchedulingConfigurer {
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        taskRegistrar.setTaskScheduler(taskScheduler());
    }

    @Bean(destroyMethod = "shutdown")
    public ThreadPoolTaskScheduler taskScheduler() {
        ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();
        scheduler.setPoolSize(10);
        scheduler.setThreadNamePrefix("ParseHtmlTask-");
        scheduler.setAwaitTerminationSeconds(600);
        scheduler.setErrorHandler(throwable -> System.out.println("error dispatch"));
        scheduler.setWaitForTasksToCompleteOnShutdown(true);
        return scheduler;
    }
}
