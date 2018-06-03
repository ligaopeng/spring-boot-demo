package com.lgp.mq.rocketmq.dragpush;

import com.qianmi.ms.starter.rocketmq.core.RocketMQTemplate;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.SendResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 类说明
 *
 * @author lgp
 * @create 2018-05-17 16:44
 */
@Slf4j
@Component
public class DragPushProducerMessage {

    @Autowired
    RocketMQTemplate rocketMQTemplate;

    public void sendPushProducerMessage() {
        String destination = "PushConsumeMessage:TagPush";
        String message = "push " + System.currentTimeMillis();
        SendResult sendResult = rocketMQTemplate.syncSend(destination, message);
        log.info("######## push producer message : {}", sendResult.toString());
    }
}
