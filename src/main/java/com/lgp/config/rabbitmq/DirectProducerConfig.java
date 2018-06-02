package com.lgp.config.rabbitmq;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

/**
 * 类说明
 * Direct Exchange是RabbitMQ默认的交换机模式，也是最简单的模式，根据key全文匹配去寻找队列
 * @author lgp
 * @create 2018-05-17 14:26
 */
@Configuration
@Profile("directProducerConfig")
public class DirectProducerConfig {

    @Bean
    Queue directQueue() {
        return new Queue("direct", true);
    }

    //-------------------配置默认的交换机模式，可以不需要配置以下-----------------------------------
    @Bean
    DirectExchange directExchange() {
        return new DirectExchange("directExchange");
    }

    //绑定一个key "direct"，当消息匹配到就会放到这个队列中
    @Bean
    Binding bindingExchangeDirectQueue(Queue directQueue, DirectExchange directExchange) {
        return BindingBuilder.bind(directQueue).to(directExchange).with("direct");
    }
}
