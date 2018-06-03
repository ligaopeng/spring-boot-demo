package com.lgp.mq.rocketmq.order;

import com.qianmi.ms.starter.rocketmq.annotation.RocketMQMessageListener;
import com.qianmi.ms.starter.rocketmq.core.RocketMQListener;
import com.qianmi.ms.starter.rocketmq.enums.ConsumeMode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 类说明
 *
 * @author lgp
 * @create 2018-06-02 14:04
 */
@Slf4j
@Component
@RocketMQMessageListener(consumerGroup = "my-consumer-async-send-message-group", topic = "AsyncTopicTest", consumeMode = ConsumeMode.ORDERLY, selectorExpress = "TagD || TagE")
public class ConsumeOrderMessageAsync implements RocketMQListener<String> {

    @Override
    public void onMessage(String message) {
        log.info("######## consume async_send_message: {}", message);
        log.info("######## consume async ########");
    }
}
