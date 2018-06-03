package com.lgp.config.rabbitmq;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.support.converter.AbstractMessageConverter;
import org.springframework.amqp.support.converter.ClassMapper;
import org.springframework.amqp.support.converter.MessageConversionException;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

/**
 * 自定义消息转换器
 * 采用FastJson完成消息转换
 * SpringBoot升级后，之前的系统内使用实体传输受到了限制，
 * 如果使用SpringBoot默认的序列化方式不会出现信任package的问题，
 * 之所以出现这个问题是因为项目使用fastjson方式进行类的序列化已经反序列化，
 * 在之前SpringBoot 1.5.10版本的时候 RabbitMQ依赖内的DefaultClassMapper类在构造函数内配置*，
 * 表示信任项目内的所有package，在SpringBoot 2.0.0版本时，DefaultClassMapper类源码构造函数进行了修改，
 * 不再信任全部package而是仅仅信任java.util、java.lang。
 *
 * @author lgp
 * @create 2018-05-22 21:17
 */
@Slf4j
public class RabbitMqFastJsonConverter extends AbstractMessageConverter {

    /**
     * 消息类型映射对象
     */
    private static ClassMapper classMapper = new RabbitMqFastJsonClassMapper();
    /**
     * 默认字符集
     */
    private static String DEFAULT_CHART_SET = "UTF-8";


    /**
     * 创建消息
     *
     * @param o                 消息对象
     * @param messageProperties 消息属性
     * @return
     */
    @Override
    protected Message createMessage(Object o, MessageProperties messageProperties) {
        byte[] bytes = null;
        try {
            String jsonString = JSON.toJSONString(o);
            log.info("================={}", jsonString);
            bytes = jsonString.getBytes(DEFAULT_CHART_SET);
        } catch (IOException e) {
            throw new MessageConversionException(
                    "Failed to convert Message content", e);
        }
        messageProperties.setContentType(MessageProperties.CONTENT_TYPE_JSON);
        messageProperties.setContentEncoding(DEFAULT_CHART_SET);
        if (bytes != null) {
            messageProperties.setContentLength(bytes.length);
        }
        classMapper.fromClass(o.getClass(), messageProperties);
        return new Message(bytes, messageProperties);
    }

    /**
     * 转换消息为对象
     *
     * @param message 消息对象
     * @return
     * @throws MessageConversionException
     */
    @Override
    public Object fromMessage(Message message) throws MessageConversionException {
        Object content = null;
        MessageProperties properties = message.getMessageProperties();
        if (properties != null) {
            String contentType = properties.getContentType();
            if (contentType != null && contentType.contains("json")) {
                String encoding = properties.getContentEncoding();
                if (encoding == null) {
                    encoding = DEFAULT_CHART_SET;
                }
                try {
                    Class<?> targetClass = classMapper.toClass(message.getMessageProperties());
                    content = convertBytesToObject(message.getBody(), encoding, targetClass);
                    log.info("================={}", JSON.toJSONString(content));
                } catch (IOException e) {
                    throw new MessageConversionException(
                            "Failed to convert Message content", e);
                }
            } else {
                log.warn("Could not convert incoming message with content-type ["
                        + contentType + "]");
            }
        }
        if (content == null) {
            content = message.getBody();
        }
        return content;
    }

    /**
     * 将字节数组转换成实例对象
     *
     * @param body     Message对象主体字节数组
     * @param encoding 字符集
     * @param clazz    类型
     * @return
     * @throws UnsupportedEncodingException
     */
    private Object convertBytesToObject(byte[] body, String encoding, Class<?> clazz) throws UnsupportedEncodingException {
        String contentAsString = new String(body, encoding);
        return JSON.parseObject(contentAsString, clazz);
    }
}
