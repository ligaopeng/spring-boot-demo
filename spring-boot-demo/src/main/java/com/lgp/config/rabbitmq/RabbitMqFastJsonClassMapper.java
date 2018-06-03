package com.lgp.config.rabbitmq;

import org.springframework.amqp.support.converter.DefaultClassMapper;

/**
 * fastjson 转换映射
 *
 * @author lgp
 * @create 2018-05-22 21:11
 */
public class RabbitMqFastJsonClassMapper extends DefaultClassMapper {

    /**
     * 构造函数初始化信任所有package
     * 如果使用RabbitMQ默认的转换方式，并不会涉及到本章遇到的信任package问题，
     * 如果想自定义消息转换并且使用DefaultClassMapper作为映射，
     * 肯定会出现信任package的问题，所以如果需要自定义转换的小伙伴，记住要设置trustedPackages。
     */
    public RabbitMqFastJsonClassMapper() {
        super();
        //setTrustedPackages("*");
    }
}
