package com.lgp.event;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationStartingEvent;
import org.springframework.context.ApplicationListener;

/**
 * 类说明
 *
 * @author lgp
 * @create 2018-05-12 17:00
 */

@Slf4j
public class MyStartingEventListener implements ApplicationListener<ApplicationStartingEvent> {



    @Override
    public void onApplicationEvent(ApplicationStartingEvent applicationStartingEvent) {
        log.info("++++++++ StartingEvent ++++++++");
    }
}
