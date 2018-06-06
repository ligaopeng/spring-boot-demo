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
@RocketMQMessageListener(consumerGroup = "my-consumer-sync-send-message-group", topic = "SyncTopicTest", consumeMode = ConsumeMode.ORDERLY, selectorExpress = "TagA || TagB || TagC")
public class ConsumeOrderMessageSync implements RocketMQListener<String> {

    @Override
    public void onMessage(String message) {
        log.info("######## consume sync_send_message: {}", message);
        try {
            Thread.sleep(10000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.info("######## consume sync ########");
    }
}
