package com.lgp.config.rabbitmq;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

/**
 * 类说明
 * Fanout 就是我们熟悉的广播模式或者订阅模式，给Fanout交换机发送消息，绑定了这个交换机的所有队列都收到这个消息
 *
 * @author lgp
 * @create 2018-05-17 14:33
 */
@Configuration
@Profile("fanoutProducerConfig")
public class FanoutProducerConfig {

    final static String LGP = "fanout.lgp.net";

    final static String LIGP = "fanout.ligp.com";

    @Bean
    public Queue queueLgp() {
        return new Queue(LGP);
    }

    @Bean
    public Queue queueLiGp() {
        return new Queue(LIGP);
    }

    /**
     * 任何发送到Fanout Exchange的消息都会被转发到与该Exchange绑定(Binding)的所有队列上。
     */
    @Bean
    FanoutExchange fanoutExchange() {
        return new FanoutExchange("fanoutExchange");
    }

    @Bean
    Binding bindingExchangeQueueLgp(Queue queueLgp, FanoutExchange fanoutExchange) {
        return BindingBuilder.bind(queueLgp).to(fanoutExchange);
    }

    @Bean
    Binding bindingExchangeQueueLiGp(Queue queueLiGp, FanoutExchange fanoutExchange) {
        return BindingBuilder.bind(queueLiGp).to(fanoutExchange);
    }
}
