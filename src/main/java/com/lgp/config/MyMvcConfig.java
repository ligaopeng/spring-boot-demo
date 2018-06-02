package com.lgp.config;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.lgp.interceptor.JwtTokenInterceptor;
import com.lgp.interceptor.WebLogInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * 类说明
 * WebMvcConfigurationSupport 此类是对WebMvcConfigurerAdapter的替换和扩展
 * @author lgp
 * @create 2018-04-25 21:27
 */
@Configuration
public class MyMvcConfig implements WebMvcConfigurer {


    /**
     * 配置大量相同的URL跳转，简化配置,快捷
     */
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/websocket").setViewName("/index");
        registry.addViewController("/login").setViewName("/login");
        registry.addViewController("/chat").setViewName("/chat");
        registry.addViewController("/cors").setViewName("/cors");
        registry.addViewController("/upload").setViewName("/upload");
    }
//
//    /**
//     * 静态资源映射
//     * addResourceLocations 存放文件的目录
//     * addResourceHandler 对外暴露的访问路径
//     */
//    @Override
//    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
//    }

    /**
     * 修改自定义消息转换器
     * FastJson SerializerFeatures
     * WriteNullListAsEmpty  ：List字段如果为null,输出为[],而非null
     * WriteNullStringAsEmpty ： 字符类型字段如果为null,输出为"",而非null
     * DisableCircularReferenceDetect ：消除对同一对象循环引用的问题，默认为false（如果不配置有可能会进入死循环）
     * WriteNullBooleanAsFalse：Boolean字段如果为null,输出为false,而非null
     * WriteMapNullValue：是否输出值为null的字段,默认为false。
     */
    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
//        //调用父类的配置
//        super.configureMessageConverters(converters);
        //创建fastJson消息转换器
        FastJsonHttpMessageConverter fastConverter = new FastJsonHttpMessageConverter();
        //创建配置类
        FastJsonConfig fastJsonConfig = new FastJsonConfig();
        //修改配置返回内容的过滤
        fastJsonConfig.setSerializerFeatures(
                SerializerFeature.DisableCircularReferenceDetect,
                SerializerFeature.WriteMapNullValue,
                SerializerFeature.WriteNullStringAsEmpty,
                SerializerFeature.WriteNullListAsEmpty
        );
        fastConverter.setFastJsonConfig(fastJsonConfig);
        //将fastjson添加到视图消息转换器列表内
        converters.add(fastConverter);
    }

    /**
     * 注册拦截器
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new WebLogInterceptor());
        registry.addInterceptor(new JwtTokenInterceptor());
    }


    /**
     * 第一种方法
     * WebMvcConfigurer的配置最终将会转换为RequestMappingHandlerMapping，
     * 而这个Handler最终是被DispatcherServlet调用，也就是说，方式一的配置将会在Servlet中被调用。
     *
     * addMapping配置可以跨域的路径，可以任意配置
     * allowedMethods 允许所有的请求方法访问该资源服务器，如：POST\GET\PUT等
     * allowedOrigins允许所有的请求域名访问我们的跨域资源，可以固定单条或者多条内容
     * allowedHeaders允许所有的请求header  访问，可以自定义设置任意请求头信息
     * 添加支持CORS跨域访问, 支持站外Ajax请求访问的跨域资源
     */
    @Override
    public void addCorsMappings(CorsRegistry registry){
        registry.addMapping("/**")
                .allowedMethods("*")
                .allowedOrigins("*")
                .allowedHeaders("*");
    }


    /**
     *第二种方法
     * 配置的Filter将会被springboot自动配置到Tomcat或其他web容器中。
     * ServletContextInitializerBeans#addAdaptableBeans方法中，
     * 将自动查找spring容器中存在的Filter实现，并且根据@Order或Order来判断Filter的排序
     * @return
     */

//    @Bean
//    public FilterRegistrationBean corsFilter() {
//        UrlBasedCorsConfigurationSource masterslave1 = new UrlBasedCorsConfigurationSource();
//        CorsConfiguration config = new CorsConfiguration();
//        config.addAllowedOrigin("*");
//        config.setAllowCredentials(true);
//        config.addAllowedHeader("*");
//        config.addAllowedMethod("*");
//        masterslave1.registerCorsConfiguration("/**", config);
//        FilterRegistrationBean bean = new FilterRegistrationBean(new CorsFilter(masterslave1));
//        bean.setOrder(0);
//        return bean;
//    }

}
