package com.lgp.config.rabbitmq;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.boot.autoconfigure.amqp.RabbitAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;


/**
 * 类说明
 *
 * @author lgp
 * @create 2018-05-15 21:40
 */
@Configuration
@Profile("topicProducerConfig")
@Import({RabbitAutoConfiguration.class})
public class TopicProducerConfig {

    final static String MESSAGE = "topic.message";

    final static String MESSAGES = "topic.message.s";

    final static String YMQ = "topic.ymq";


    @Bean
    Queue queueMessage() {
        Queue queue = new Queue(MESSAGE, true);
        return queue;
    }

    @Bean
    Queue queueMessages() {
        Queue queue = new Queue(MESSAGES, true);
        return queue;
    }

    @Bean
    Queue queueYmq() {
        Queue queue = new Queue(YMQ, true);
        return queue;
    }

    @Bean
    TopicExchange topicExchange() {
        TopicExchange exchange = new TopicExchange("topicExchange");
        return exchange;
    }

    /**
     * 綁定队列 queueMessages() 到 topicExchange 交换机,路由键只接受完全匹配 topic.message 的队列接受者可以收到消息
     *
     * @param topicExchange
     * @return
     */
    @Bean
    Binding bindingExchangeMessage(Queue queueMessage, TopicExchange topicExchange) {
        Binding binding = BindingBuilder.bind(queueMessage).to(topicExchange).with("topic.message");
        return binding;
    }

    /**
     * 綁定队列 queueMessages() 到 topicExchange 交换机,路由键只要是以 topic.message 开头的队列接受者可以收到消息
     *
     * @param topicExchange
     * @return
     */
    @Bean
    Binding bindingExchangeMessages(Queue queueMessages, TopicExchange topicExchange) {
        Binding binding = BindingBuilder.bind(queueMessages).to(topicExchange).with("topic.message.#");
        return binding;
    }


    //綁定队列 queueYmq() 到 topicExchange 交换机,路由键只要是以 topic 开头的队列接受者可以收到消息
    @Bean
    Binding bindingExchangeYmq(Queue queueYmq, TopicExchange topicExchange) {
        return BindingBuilder.bind(queueYmq).to(topicExchange).with("topic.#");
    }
}


//    @Bean
//    RabbitAdmin rabbitAdmin(ConnectionFactory connectionFactory) {
//        return new RabbitAdmin(connectionFactory);
//    }

//    @Bean
//    Queue queueMessage(RabbitAdmin rabbitAdmin) {
//        Queue queue = new Queue(MESSAGE, true);
//        return queue;
//    }
//@Bean
//TopicExchange topicExchange(RabbitAdmin rabbitAdmin) {
//    TopicExchange exchange = new TopicExchange("topicExchange");
//    rabbitAdmin.declareExchange(exchange);
//    return exchange;
//}
//    /**
//     * 将队列queue.foo与exchange绑定，binding_key为queue.foo,就是完全匹配
//     * @param topicExchange
//     * @return
//     */
//    @Bean
//    Binding bindingExchangeFoo(Queue queueFoo, TopicExchange topicExchange, RabbitAdmin rabbitAdmin) {
//        Binding binding = BindingBuilder.bind(queueFoo).to(topicExchange).with("queue.foo");
//        rabbitAdmin.declareBinding(binding);
//        return binding;
//    }
//
//    /**
//     * 将队列queue.foo与exchange绑定，binding_key为queue.foo,就是完全匹配
//     * @param topicExchange
//     * @return
//     */
//    @Bean
//    Binding bindingExchangeFoo(Queue queueFoo, TopicExchange topicExchange, RabbitAdmin rabbitAdmin) {
//        Binding binding = BindingBuilder.bind(queueFoo).to(topicExchange).with("queue.foo");
//        rabbitAdmin.declareBinding(binding);
//        return binding;
//    }
//
//    /**
//     * 将队列queue.bar与exchange绑定，binding_key为queue.#,模糊匹配
//     * @param topicExchange
//     * @return
//     */
//    @Bean
//    Binding bindingExchangeBar(Queue queueBar, TopicExchange topicExchange, RabbitAdmin rabbitAdmin) {
//        Binding binding = BindingBuilder.bind(queueBar).to(topicExchange).with("queue.#");
//        rabbitAdmin.declareBinding(binding);
//        return binding;
//    }
//
//
//
//    @Bean
//    @Qualifier("rabbitMessagingTemplate")
//    public RabbitMessagingTemplate rabbitMessagingTemplate(RabbitTemplate rabbitTemplate) {
//        RabbitMessagingTemplate messagingTemplate = new RabbitMessagingTemplate();
//        messagingTemplate.setMessageConverter(jackson2MessageConverter());
//        messagingTemplate.setRabbitTemplate(rabbitTemplate);
//        return messagingTemplate;
//    }
//
//
//    @Bean
//    public MappingJackson2MessageConverter jackson2MessageConverter () {
//        MappingJackson2MessageConverter messageConverter = new MappingJackson2MessageConverter();
//        return messageConverter;
//    }