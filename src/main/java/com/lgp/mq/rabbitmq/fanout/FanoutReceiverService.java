package com.lgp.mq.rabbitmq.fanout;

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
public class FanoutReceiverService {


    @RabbitListener(queues = "fanout.lgp.net")
    public void receiveLgpQueue(User user) {
        System.out.println("Received to fanout.lgp.net<" + JSON.toJSONString(user) + ">");
    }


    @RabbitListener(queues = "fanout.ligp.com")
    public void receiveLiGpQueue(User user) {
        System.out.println("Received to fanout.ligp.com<" + JSON.toJSONString(user) + ">");
    }


}
