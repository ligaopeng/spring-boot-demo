package com.lgp.mq.rocketmq.order;

import com.alibaba.fastjson.JSON;
import com.lgp.mq.rocketmq.MySendCallBack;
import com.qianmi.ms.starter.rocketmq.core.RocketMQTemplate;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.SendResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 类说明
 *
 * @author lgp
 * @create 2018-06-02 12:58
 */
@Slf4j
@Component
public class ProducerOrderMessage {

    @Autowired
    RocketMQTemplate rocketMQTemplate;


    public void syncSendMessage() {
        String[] tags = {"TagA", "TagB", "TagC"};
        for (int i = 0; i < 100; i++) {
            int orderId = i%10;
            selectorQueue();
            String destination = "SyncTopicTest:" + tags[i % tags.length]; //destination formats: `topicName:tags`
            String message = "Hello rocketMQ sync message " + orderId + " " + destination;
            String hashKey = String.valueOf(orderId);
            SendResult sendResult = rocketMQTemplate.syncSendOrderly(destination, message, hashKey);
            log.info("######## producer sendResult :{}", destination + "-" + JSON.toJSONString(sendResult));
        }
    }


    public void asyncSendMessage() {
        String[] tags = {"TagD", "TagE"};
        for (int i = 0; i < 100; i++) {
            int orderId = i%10;
            selectorQueue();
            String destination = "AsyncTopicTest:" + tags[i % tags.length]; //destination formats: `topicName:tags`
            String message = "Hello rocketMQ async message " + orderId + " " + destination;
            String hashKey = String.valueOf(orderId);
            rocketMQTemplate.asyncSendOrderly(destination, message, hashKey, new MySendCallBack());
            log.info("######## producer sendResult :{}", destination);
        }
    }

    private void selectorQueue() {
        rocketMQTemplate.setMessageQueueSelector((list, message, arg) -> {
            String s = (String) arg;
            int id = Integer.parseInt(s);
            int index = id % list.size();
            return list.get(index);
        });
    }
}
