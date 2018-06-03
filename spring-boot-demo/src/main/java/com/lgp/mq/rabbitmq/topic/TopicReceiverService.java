package com.lgp.mq.rabbitmq.topic;

import com.alibaba.fastjson.JSON;
import com.lgp.entity.user.User;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * 类说明
 *
 * @author lgp
 * @create 2018-05-15 22:15
 */
@Component
public class TopicReceiverService {


    @RabbitListener(queues = "topic.message")

    public void receiveMessageQueue(User user) {
        System.out.println("Received to topic.message<" + JSON.toJSONString(user) + ">");
    }

    @RabbitListener(queues = "topic.message.s")
    public void receiveMessagesQueue(User user) {
        System.out.println("Received to topic.message.s<" + JSON.toJSONString(user) + ">");
    }


    @RabbitListener(queues = "topic.ymq")
    public void receiveYmqQueue(User user) throws Exception {
        System.out.println("Received to topic.ymq<" + JSON.toJSONString(user) + ">");
        Thread.sleep(10000L);
    }


}
