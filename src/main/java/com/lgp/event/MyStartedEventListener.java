package com.lgp.event;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationListener;

/**
 * 类说明
 *
 * @author lgp
 * @create 2018-05-12 17:00
 */

@Slf4j
public class MyStartedEventListener implements ApplicationListener<ApplicationStartedEvent> {


    @Override
    public void onApplicationEvent(ApplicationStartedEvent applicationStartedEvent) {
        log.info("++++++++ StartedEvent ++++++++");
    }
}
