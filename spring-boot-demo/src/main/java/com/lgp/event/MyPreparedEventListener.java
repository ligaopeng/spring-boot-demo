package com.lgp.event;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationPreparedEvent;
import org.springframework.context.ApplicationListener;

/**
 * 类说明
 * spring boot上下文context创建完成，但此时spring中的bean是没有完全加载完成的.
 * 在获取完上下文后，可以将上下文传递出去做一些额外的操作。
 * 在该监听器中是无法获取自定义bean并进行操作的
 *
 * @author lgp
 * @create 2018-05-12 17:00
 */

@Slf4j
public class MyPreparedEventListener implements ApplicationListener<ApplicationPreparedEvent> {

    @Override
    public void onApplicationEvent(ApplicationPreparedEvent applicationPreparedEvent) {
        log.info("++++++++ PreparedEvent ++++++++");
    }
}
