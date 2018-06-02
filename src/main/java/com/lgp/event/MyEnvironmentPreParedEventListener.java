package com.lgp.event;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationEnvironmentPreparedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.env.PropertySource;

import java.util.Iterator;

/**
 * 类说明
 *#执行顺序
 * #ApplicationStartingEvent
 * #ApplicationEnvironmentPreparedEvent
 * #ApplicationPreparedEvent
 * #ApplicationStartedEvent <= 2.0新增的事件
 * #ApplicationReadyEvent
 * #ApplicationFailedEvent
 *
 * spring boot 对应Enviroment已经准备完毕，但此时上下文context还没有创建。
 * 在该监听中获取到ConfigurableEnvironment后可以对配置信息做操作，例如：修改默认的配置信息，增加额外的配置信息等等~~~
 * @author lgp
 * @create 2018-05-12 16:36
 */
@Slf4j
public class MyEnvironmentPreParedEventListener implements ApplicationListener<ApplicationEnvironmentPreparedEvent> {


    @Override
    public void onApplicationEvent(ApplicationEnvironmentPreparedEvent event) {
        log.info("++++++++ EnvironmentPreparedEvent ++++++++");
        ConfigurableEnvironment environment = event.getEnvironment();
        MutablePropertySources mps = environment.getPropertySources();
        if (mps != null) {
            Iterator<PropertySource<?>> iterator = mps.iterator();
            while (iterator.hasNext()) {
                PropertySource<?> ps = iterator.next();
                log.info("ps.getName:{};ps.getSource:{};ps.getClass:{}", ps.getName(), ps.getSource(), ps.getClass());
            }
        }
    }
}
