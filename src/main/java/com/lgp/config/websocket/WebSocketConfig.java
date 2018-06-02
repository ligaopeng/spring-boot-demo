package com.lgp.config.websocket;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

/**
 * 广播式消息，
 *
 * @author lgp
 * @create 2018-04-26 15:51
 */
@Configuration
@EnableWebSocketMessageBroker//开启websocket支持，使用STOMP协议来传输基于代理的消息，这时控制器支持使用@MessageMapping
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    /**
     * 注册STOMP协议的节点，并映射指定的URL
     * @param stompEndpointRegistry
     */

    @Override
    public void registerStompEndpoints(StompEndpointRegistry stompEndpointRegistry) {
        stompEndpointRegistry.addEndpoint("/endpointWisely").withSockJS();//注册一个STOMP的endoint,并指定使用ScokJS，广播
        stompEndpointRegistry.addEndpoint("/endpointChat").withSockJS();//点对点
    }


    /**
     * 配置消息代理
     */
    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.enableSimpleBroker("/queue","/topic");//广播式应配置一个/topic消息代理，点对点/queue
    }
}

