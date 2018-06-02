package com.lgp.mq.rabbitmq;

import com.lgp.entity.user.User;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 类说明
 *
 * @author lgp
 * @create 2018-05-15 22:12
 */
@Component
public class SenderService {

    private static final String TOPIC_EXCHANGE = "topicExchange";

    private static final String DIRECT_EXCHANGE = "directExchange";

    private static final String FANOUT_EXCHANGE = "fanoutExchange";

    private final static String MESSAGE = "topic.message";

    private final static String MESSAGES = "topic.message.s";

    private final static String YMQ = "topic.ymq";

    private final static String DIRECT = "direct";

    private final static String LGP = "fanout.lgp.net";

    private final static String LIGP = "fanout.ligp.com";

    @Autowired
    private AmqpTemplate rabbitTemplate;


    public void senderTopicToMessage(final User user) {
        rabbitTemplate.convertAndSend(TOPIC_EXCHANGE, MESSAGE, user);
    }


    public void senderTopicMessages(final User user) {
        rabbitTemplate.convertAndSend(TOPIC_EXCHANGE, MESSAGES, user);
    }

    public void senderTopicYmq(final User user) {
        rabbitTemplate.convertAndSend(TOPIC_EXCHANGE, YMQ, user);
    }

    public void senderDirectToMessage(final User user) {
        rabbitTemplate.convertAndSend(DIRECT_EXCHANGE, DIRECT, user);
    }

    public void senderFanoutToLgp(final User user) {
        rabbitTemplate.convertAndSend(FANOUT_EXCHANGE, LGP, user);
    }

    public void senderFanoutToLIGP(final User user) {
        rabbitTemplate.convertAndSend(FANOUT_EXCHANGE, LIGP, user);
    }
}

