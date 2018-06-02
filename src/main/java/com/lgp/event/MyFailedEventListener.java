package com.lgp.event;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationFailedEvent;
import org.springframework.context.ApplicationListener;

/**
 * 类说明
 * spring boot启动异常时执行事件
 * 在异常发生时，最好是添加虚拟机对应的钩子进行资源的回收与释放，能友善的处理异常信息。
 * 默认开启资源的回收与释放
 * @author lgp
 * @create 2018-05-12 17:00
 */

@Slf4j
public class MyFailedEventListener implements ApplicationListener<ApplicationFailedEvent> {

    @Override
    public void onApplicationEvent(ApplicationFailedEvent event) {
        log.info("++++++++ failedEvent ++++++++");
    }
}
