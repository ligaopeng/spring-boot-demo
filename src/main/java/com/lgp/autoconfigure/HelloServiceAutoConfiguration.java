package com.lgp.autoconfigure;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 类说明
 *
 * @author lgp
 * @create 2018-04-24 21:03
 */
@Configuration
@EnableConfigurationProperties(HelloServiceProperties.class)//开启属性注入，使用@Autowired注入
@ConditionalOnClass(HelloService.class)//当HelloService在类路径的条件下
@ConditionalOnProperty(prefix = "hello", value = "enabled", matchIfMissing = true)//当设置hello=enable的情况下，如果没有设置则默认为true,即条件符合
public class HelloServiceAutoConfiguration {

    @Autowired
    private HelloServiceProperties helloServiceProperties;//当设置hello=enable的情况下，如果没有设置则默认为true,即条件符合


    @Bean//向使用java配置的方式配置HelloService这个bean
    @ConditionalOnMissingBean(HelloService.class)//当容器中没有这个bean，则新建bean
    public HelloService helloService(){
        HelloService helloService = new HelloService();
        helloService.setMsg(helloServiceProperties.getMsg());
        return helloService;
    }
}
