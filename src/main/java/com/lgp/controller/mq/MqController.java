package com.lgp.controller.mq;

import com.lgp.entity.user.User;
import com.lgp.mq.rabbitmq.SenderService;
import com.lgp.mq.rocketmq.boot.ProducerMessage;
import com.lgp.mq.rocketmq.dragpush.DragPushProducerMessage;
import com.lgp.mq.rocketmq.order.ProducerOrderMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 类说明
 *
 * @author lgp
 * @create 2018-05-15 22:22
 */
@RestController
@RequestMapping(value = "mq")
public class MqController {

    @Autowired
    SenderService senderService;

    @Autowired
    ProducerMessage producerMessage;

    @Autowired
    ProducerOrderMessage producerOrderMessage;

    @Autowired
    DragPushProducerMessage dragPushProducerMessage;

    @PostMapping(value = "sendRabbitmqUser")
    public void sendRabbitmqUser() {
        User user = new User();
        user.setLoginName("121212");
        user.setId(211);
        user.setPwd("qq");
        for (int i =0 ; i<100; i++) {
            user.setId(i);
            senderService.senderTopicToMessage(user);
            senderService.senderDirectToMessage(user);
            senderService.senderFanoutToLgp(user);
            senderService.senderFanoutToLIGP(user);
            senderService.senderTopicYmq(user);
            senderService.senderTopicMessages(user);
        }
    }

    @PostMapping(value = "sendRocketMqMessage")
    public void sendRocketMqMessage(){
        producerMessage.sendProducerMessage();
    }


    @PostMapping(value = "sendSyncMqMessage")
    public void sendSyncMqMessage(){
        producerOrderMessage.syncSendMessage();
    }

    @PostMapping(value = "sendAsyncMqMessage")
    public void sendAsyncMqMessage(){
        producerOrderMessage.asyncSendMessage();
    }

    @PostMapping(value = "sendPushMqMessage")
    public void sendPushMqMessage(){
        dragPushProducerMessage.sendPushProducerMessage();
    }
}

