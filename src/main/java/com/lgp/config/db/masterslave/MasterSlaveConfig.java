package com.lgp.config.db.masterslave;

import com.lgp.config.db.masterslave.dataSource.DynamicDataSource;
import com.lgp.config.db.masterslave.manager.DynamicDataSourceTransactionManager;
import com.lgp.config.db.masterslave.plugin.DynamicPlugin;
import com.lgp.interceptor.SqlCostInterceptor;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.*;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;
import java.util.List;

/**
 * 类说明
 *
 * @author lgp
 * @create 2018-05-09 16:13
 */
@Configuration
@Profile("master-slave-datasource")
@Import({SqlCostInterceptor.class, DynamicPlugin.class})
// 扫描 Mapper 接口并容器管理
@MapperScan(basePackages = MasterSlaveConfig.PACKAGE, sqlSessionFactoryRef = "sqlSessionFactory")
public class MasterSlaveConfig {

    // 精确到 cluster 目录，以便跟其他数据源隔离
    static final String PACKAGE = "com.lgp.dao.**";


    static final String MAPPER_LOCATION = "classpath:mapper/**/*.xml";

    /**
     * 一主
     */
    @Autowired
    @Qualifier("masterDataSource")
    private DataSource masterDataSource;

    /**
     * 多从
     */
    @Autowired
    @Qualifier("slaveDataSources")
    private List<DataSource> slaveDataSources;

    @Autowired
    private SqlCostInterceptor sqlCostInterceptor;//sql语句打印日志拦截器

    @Autowired
    private DynamicPlugin dynamicPlugin;

    @Bean
    public DynamicDataSource dynamicDataSource(){
        DynamicDataSource dynamicDataSource = new DynamicDataSource();
        dynamicDataSource.setMasterDataSource(masterDataSource);
        dynamicDataSource.setSlavesDataSource(slaveDataSources);
        return dynamicDataSource;
    }

    @Bean(name = "sqlSessionFactory")
    public SqlSessionFactory sqlSessionFactory(@Qualifier("dynamicDataSource") DataSource dynamicDataSource)
            throws Exception {
        final SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(dynamicDataSource);
        PathMatchingResourcePatternResolver patternResolver = new PathMatchingResourcePatternResolver();
        sessionFactory.setMapperLocations(patternResolver.getResources(MasterSlaveConfig.MAPPER_LOCATION));
        sessionFactory.setConfigLocation(patternResolver.getResource("classpath:mybatis/mybatis-config.xml"));
        sessionFactory.setPlugins(new Interceptor[]{dynamicPlugin});
        return sessionFactory.getObject();
    }



    /**
     * 配置事务管理器
     */
    @Bean
    @Primary
    public DataSourceTransactionManager transactionManager() {
        return new DynamicDataSourceTransactionManager(dynamicDataSource());
    }
}
