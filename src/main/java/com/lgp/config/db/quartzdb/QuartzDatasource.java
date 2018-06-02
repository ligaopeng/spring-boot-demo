package com.lgp.config.db.quartzdb;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import javax.sql.DataSource;

/**
 * 类说明
 *
 * @author lgp
 * @create 2018-05-14 21:23
 */
@Configuration
@Profile("quartzDatasource")
public class QuartzDatasource {


    @Bean(name = "quartzDataSource")//装配该方法返回值为quartzDataSource管理bean
    @Qualifier("quartzDataSource")//spring装配bean唯一标识
    @ConfigurationProperties(prefix="quartz.druid")//application.properties文件内配置数据源的前缀
    public DataSource quartzDataSource(){return DruidDataSourceBuilder.create().build();}



}
