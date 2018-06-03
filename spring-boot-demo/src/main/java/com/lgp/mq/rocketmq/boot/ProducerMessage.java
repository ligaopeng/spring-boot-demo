package com.lgp.mq.rocketmq.boot;

import com.qianmi.ms.starter.rocketmq.core.RocketMQTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

/**
 * 类说明
 *
 * @author lgp
 * @create 2018-05-17 16:44
 */
@Slf4j
@Component
public class ProducerMessage {

    @Autowired
    RocketMQTemplate rocketMQTemplate;

    public void sendProducerMessage() {
        rocketMQTemplate.convertAndSend("test-topic-1", "Hello, World!");
        rocketMQTemplate.send("test-topic-1", MessageBuilder.withPayload("Hello, World! I'm from spring message").build());
        rocketMQTemplate.convertAndSend("test-topic-2", new OrderPaidEvent("T_001", new BigDecimal("88.00")));
        //rocketMQTemplate.destroy();//一旦rocketMQTemplate被销毁，就不能用rocketMQTemplate再次发送任何消息。
    }
}
