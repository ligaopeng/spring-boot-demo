package com.lgp.event;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;

/**
 * 类说明
 *
 * @author lgp
 * @create 2018-05-12 17:00
 */

@Slf4j
public class MyReadyEventListener implements ApplicationListener<ApplicationReadyEvent> {


    @Override
    public void onApplicationEvent(ApplicationReadyEvent applicationReadyEvent) {
        log.info("++++++++ ReadyEvent ++++++++");
    }
}
