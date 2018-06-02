package com.lgp.config.rabbitmq;

import com.lgp.mq.rabbitmq.direct.DirectReceiverService;
import com.lgp.mq.rabbitmq.fanout.FanoutReceiverService;
import com.lgp.mq.rabbitmq.topic.TopicReceiverService;
import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitListenerConfigurer;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.RabbitListenerEndpointRegistrar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.handler.annotation.support.DefaultMessageHandlerMethodFactory;

/**
 * 类说明
 *
 * @author lgp
 * @create 2018-05-15 22:01
 */
@Configuration
@Profile("consumeConfig")
@EnableRabbit
public class ConsumeConfig implements RabbitListenerConfigurer {

    @Autowired
    TopicReceiverService topicReceiverService;

    @Autowired
    DirectReceiverService directReceiverService;

    @Autowired
    FanoutReceiverService fanoutReceiverService;

    @Bean
    public DefaultMessageHandlerMethodFactory myHandlerMethodFactory(){
        DefaultMessageHandlerMethodFactory handlerMethodFactory = new DefaultMessageHandlerMethodFactory();
        handlerMethodFactory.setMessageConverter(new MappingJackson2MessageConverter());
        return handlerMethodFactory;
    }

    @Bean
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory (ConnectionFactory connectionFactory) {
        SimpleRabbitListenerContainerFactory listenerContainerFactory = new SimpleRabbitListenerContainerFactory();
        listenerContainerFactory.setConnectionFactory(connectionFactory);
        //listenerContainerFactory.setPrefetchCount(5);
        listenerContainerFactory.setAcknowledgeMode(AcknowledgeMode.AUTO);
        return listenerContainerFactory;
    }

    @Override
    public void configureRabbitListeners(RabbitListenerEndpointRegistrar rabbitListenerEndpointRegistrar) {
        rabbitListenerEndpointRegistrar.setMessageHandlerMethodFactory(myHandlerMethodFactory());
    }
}

/**
 * SpringBoot升级后，之前的系统内使用实体传输受到了限制，如果使用SpringBoot默认的序列化方式不会出现信任package的问题，
 * 之所以出现这个问题是因为项目使用fastjson方式进行类的序列化已经反序列化，在之前SpringBoot 1.5.10版本的时候 RabbitMQ依赖内的DefaultClassMapper类在构造函数内配置*，
 * 表示信任项目内的所有package，在SpringBoot 2.0.0版本时，DefaultClassMapper类源码构造函数进行了修改，不再信任全部package而是仅仅信任java.util、java.lang
 *
 */

//@Configuration
//public class RabbitMqConfiguration {
//
//
//    /**
//     * 配置消息队列模版
//     * 并且设置MessageConverter为自定义FastJson转换器
//     * @param connectionFactory
//     * @return
//     */
//    @Bean
//    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
//        RabbitTemplate template = new RabbitTemplate(connectionFactory);
//        template.setMessageConverter(new RabbitMqFastJsonConverter());
//        return template;
//    }
//
//    /**
//     * 自定义队列容器工厂
//     * 并且设置MessageConverter为自定义FastJson转换器
//     * @param connectionFactory
//     * @return
//     */
//    @Bean
//    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(ConnectionFactory connectionFactory) {
//        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
//        factory.setConnectionFactory(connectionFactory);
//        factory.setMessageConverter(new RabbitMqFastJsonConverter());
//        factory.setDefaultRequeueRejected(false);
//        return factory;
//    }
//
//}
