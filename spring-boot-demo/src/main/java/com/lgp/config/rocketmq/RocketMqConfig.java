package com.lgp.config.rocketmq;

import com.qianmi.ms.starter.rocketmq.RocketMQAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;

/**
 * 类说明
 *
 * @author lgp
 * @create 2018-05-17 16:41
 */
@Configuration
@Profile("rocketMqConfig")
@Import(value = {RocketMQAutoConfiguration.class})
public class RocketMqConfig {

}
