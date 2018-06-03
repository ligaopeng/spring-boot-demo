package com.lgp.mq.rocketmq.dragpush;

import com.qianmi.ms.starter.rocketmq.annotation.RocketMQMessageListener;
import com.qianmi.ms.starter.rocketmq.core.RocketMQListener;
import com.qianmi.ms.starter.rocketmq.core.RocketMQPushConsumerLifecycleListener;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.common.UtilAll;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.springframework.stereotype.Component;

/**
 * 类说明
 *
 * @author lgp
 * @create 2018-06-02 15:27
 */
@Slf4j
@Component
@RocketMQMessageListener(consumerGroup = "push_consume_group", topic = "PushConsumeMessage", selectorExpress = "TagPush")
public class PushConsumerMessage implements RocketMQListener<String>, RocketMQPushConsumerLifecycleListener {


    @Override
    public void prepareStart(final DefaultMQPushConsumer consumer) {
        consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
        consumer.setConsumeTimestamp(UtilAll.timeMillisToHumanString3(System.currentTimeMillis()));
    }

    @Override
    public void onMessage(String message) {
        log.info("######## push consume message :{}", message);
    }
}
