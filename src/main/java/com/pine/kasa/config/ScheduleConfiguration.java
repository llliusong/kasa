package com.pine.kasa.config;

import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

import java.util.concurrent.Executors;

/**
 * nohup java -Xmx512m -Xms512m -jar communityTask.jar &
 *
 * https://docs.spring.io/spring/docs/3.2.x/spring-framework-reference/html/scheduling.html
 * Created by pine on  2019/4/2 上午10:20.
 */
//@Configuration
public class ScheduleConfiguration implements SchedulingConfigurer {
    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        taskRegistrar.setScheduler(Executors.newScheduledThreadPool(12));
    }
}