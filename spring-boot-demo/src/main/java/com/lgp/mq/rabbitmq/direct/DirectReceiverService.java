package com.lgp.mq.rabbitmq.direct;

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
public class DirectReceiverService {


    @RabbitListener(queues = "direct")
    public void receiveDIRECTQueue(User user) {
        System.out.println("Received to direct<" + JSON.toJSONString(user) + ">");
    }


}
