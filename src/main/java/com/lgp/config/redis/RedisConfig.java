package com.lgp.config.redis;

import com.alibaba.fastjson.parser.ParserConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonRedisSerializer;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.lang.reflect.Method;
import java.time.Duration;
import java.util.concurrent.CountDownLatch;

/**
 * 类说明
 *
 * @author lgp
 * @create 2018-05-01 19:27
 */
@Configuration
@EnableCaching//开启声明式缓存
public class RedisConfig extends CachingConfigurerSupport {

    /**
     * 自定义生成key的规则
     * @return
     */
    @Override
    public KeyGenerator keyGenerator() {
        return new KeyGenerator() {
            @Override
            public Object generate(Object o, Method method, Object... objects) {
                //格式化缓存key字符串
                StringBuilder sb = new StringBuilder();
                //追加类名
                sb.append(o.getClass().getName());
                //追加方法名
                sb.append(method.getName());
                //遍历参数并且追加
                for (Object obj : objects) {
                    sb.append(obj.toString());
                }
                System.out.println("调用Redis缓存Key : " + sb.toString());
                return sb.toString();
            }
        };
    }

    /**
     * 采用RedisCacheManager作为缓存管理器
     * @param connectionFactory
     * @return
     */
    @Bean
    public CacheManager cacheManager(RedisConnectionFactory connectionFactory) {
        //设置有效期
        RedisCacheConfiguration redisCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig();
        redisCacheConfiguration.entryTtl(Duration.ofMinutes(3L));
        //获取缓存管理器
        RedisCacheManager redisCacheManager = RedisCacheManager.builder(connectionFactory).cacheDefaults(redisCacheConfiguration).build();
        return redisCacheManager;
    }

    @Bean
    public RedisSerializer fastJsonRedisSerializer() {
        ParserConfig.getGlobalInstance().setAutoTypeSupport(true);
        FastJsonRedisSerializer fastJsonRedisSerializer = new FastJsonRedisSerializer(Object.class);
        //创建配置类
        FastJsonConfig fastJsonConfig = new FastJsonConfig();
        //修改配置返回内容的过滤
        fastJsonConfig.setSerializerFeatures(
                SerializerFeature.DisableCircularReferenceDetect,
                SerializerFeature.WriteMapNullValue,
                SerializerFeature.WriteNullStringAsEmpty,
                SerializerFeature.WriteNullListAsEmpty
        );
        fastJsonRedisSerializer.setFastJsonConfig(fastJsonConfig);
        return fastJsonRedisSerializer;
    }
    /**
     * RedisTemplate
     * @Description: 防止redis入库序列化乱码的问题
     * @return     返回类型
     * @date 2018/4/12 10:54
     */
    @Bean("redisTemplate")
    public RedisTemplate<Object, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory, RedisSerializer fastJsonRedisSerializer){
        RedisTemplate<Object, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        redisTemplate.setEnableTransactionSupport(true);//redis开启事物
        redisTemplate.setHashKeySerializer(fastJsonRedisSerializer);//hash  key使用fastJson  的序列化
        redisTemplate.setHashValueSerializer(fastJsonRedisSerializer);//hash  value使用fastJson  的序列化
        redisTemplate.setKeySerializer(new StringRedisSerializer());//key序列化
        redisTemplate.setValueSerializer(fastJsonRedisSerializer);  //value序列化
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }

    @Bean("stringRedisTemplate")
    public RedisTemplate<String, String> stringRedisTemplate(RedisConnectionFactory redisConnectionFactory, RedisSerializer fastJsonRedisSerializer) {
        StringRedisTemplate stringRedisTemplate = new StringRedisTemplate();
        stringRedisTemplate.setConnectionFactory(redisConnectionFactory);
        stringRedisTemplate.setEnableTransactionSupport(true);//redis开启事物
        stringRedisTemplate.setHashKeySerializer(fastJsonRedisSerializer);//hash  key使用fastJson  的序列化
        stringRedisTemplate.setHashValueSerializer(fastJsonRedisSerializer);//hash  value使用fastJson  的序列化
        stringRedisTemplate.setKeySerializer(new StringRedisSerializer());//key序列化
        stringRedisTemplate.setValueSerializer(fastJsonRedisSerializer);  //value序列化
        stringRedisTemplate.afterPropertiesSet();
        return stringRedisTemplate;
    }

    /**
     * 必要的redis消息队列连接工厂
     * @return
     */
    @Bean
    public CountDownLatch latch(){
        return new CountDownLatch(1);//指定了计数的次数 1
    }

    /**
     * 注册订阅者
     */
    @Bean
    public Receiver receiver(CountDownLatch latch){
        return new Receiver(latch);
    }


    /**
     * 注入消息监听器容器
     */
    @Bean
    public RedisMessageListenerContainer container(RedisConnectionFactory connectionFactory, MessageListenerAdapter messageListenerAdapter) {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        //订阅了一个叫msg 的通道
        container.addMessageListener(messageListenerAdapter, new PatternTopic("msg"));
        //这个container 可以添加多个 messageListener
        return container;
    }


    /**
     * 注入消息监听器容器
     */
    @Bean
    public MessageListenerAdapter listenerAdapter(Receiver receiver) {
        return new MessageListenerAdapter(receiver, "receiverMessage");
    }


}
