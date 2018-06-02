package com.lgp.config.db.multisource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

/**
 * 类说明
 *
 * @author lgp
 * @create 2018-04-27 22:36
 */
@Configuration
@Profile("cluster-datasource")
// 扫描 Mapper 接口并容器管理
@MapperScan(basePackages = ClusterDataSourceConfig.PACKAGE, sqlSessionFactoryRef = "clusterSqlSessionFactory")
public class ClusterDataSourceConfig {

    // 精确到 cluster 目录，以便跟其他数据源隔离
    static final String PACKAGE = "com.lgp.dao.cluster.**";
    static final String MAPPER_LOCATION = "classpath:mapper/cluster/**/*.xml";

    @Autowired
    @Qualifier("clusterDataSource")
    private DataSource clusterDataSource;

    @Bean(name = "clusterTransactionManager")
    public DataSourceTransactionManager clusterTransactionManager() {
        return new DataSourceTransactionManager(clusterDataSource);
    }

    @Bean(name = "clusterSqlSessionFactory")
    public SqlSessionFactory clusterSqlSessionFactory(@Qualifier("clusterDataSource") DataSource clusterDataSource)
            throws Exception {
        final SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(clusterDataSource);
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        sessionFactory.setMapperLocations(resolver.getResources(ClusterDataSourceConfig.MAPPER_LOCATION));
        sessionFactory.setConfigLocation(resolver.getResource("classpath:mybatis/mybatis-config.xml"));
        return sessionFactory.getObject();
    }
}