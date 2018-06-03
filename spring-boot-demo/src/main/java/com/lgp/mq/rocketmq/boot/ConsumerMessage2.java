package com.lgp.mq.rocketmq.boot;

import com.qianmi.ms.starter.rocketmq.annotation.RocketMQMessageListener;
import com.qianmi.ms.starter.rocketmq.core.RocketMQListener;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

/**
 * 类说明
 *
 * @author lgp
 * @create 2018-05-17 16:44
 */
@Log4j2
@Component
@RocketMQMessageListener(topic = "test-topic-2", consumerGroup = "my-consumer_test-topic-2")
public class ConsumerMessage2 implements RocketMQListener<String> {


    public void onMessage(String message) {
        log.info("######## consume received orderPaidEvent: {}", message);
    }

}
