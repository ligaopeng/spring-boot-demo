package com.lgp.config.db;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

/**
 * 类说明
 * 经测试无需使用@enabletransactionmanagement开启事务注解；
 * 不要使用try catch把异常捕获了，会导致不能回滚；
 * MySQL数据库表引擎innoDB是支持事务的，但是MyISAM引擎是不支持事务的；
 *
 * @author lgp
 * @create 2018-05-01 17:39
 */
@Configuration
@Profile("datasource")
public class DataSourceConfig {


    //master数据源
    @Bean(name = "masterDataSource")//装配该方法返回值为userDataSource管理bean
    @Primary//配置该数据源为主数据源
    @Qualifier("masterDataSource")//spring装配bean唯一标识
    @ConfigurationProperties(prefix = "master.druid")//application.properties文件内配置数据源的前缀
    public DataSource masterDataSource() {
        DruidDataSource build = DruidDataSourceBuilder.create().build();
        return build;
    }

    //cluster数据源
    @Bean(name = "clusterDataSource")//装配该方法返回值为clusterDataSource管理bean
    @Qualifier("clusterDataSource")//spring装配bean唯一标识
    @ConfigurationProperties(prefix = "cluster.druid")//application.properties文件内配置数据源的前缀
    public DataSource clusterDataSource() {
        return DruidDataSourceBuilder.create().build();
    }


    /**
     * 从数据源集合
     *
     * @return
     */
    @Bean(name = "slaveDataSources")
    @Qualifier("slaveDataSources")//spring装配bean唯一标识
    public List<DataSource> slaveDataSources() {
        List<DataSource> slaveDataSources = new ArrayList();
        slaveDataSources.add(clusterDataSource());
        return slaveDataSources;
    }

}
