package com.lgp.config.db.masterslave1;

import com.lgp.config.db.DataSourceConfig;
import com.lgp.interceptor.SqlCostInterceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 类说明
 *
 * @author lgp
 * @create 2018-05-09 17:14
 */
@Configuration
@Profile("one-master-many-slave-datasource")
@Import({DataSourceConfig.class, SqlCostInterceptor.class})
@MapperScan(basePackages = DataSourceSqlSessionFactory.aliasesPackage, sqlSessionFactoryRef = "sqlSessionFactory")
public class DataSourceSqlSessionFactory {

    protected static Logger logger = LogManager.getLogger(LogManager.ROOT_LOGGER_NAME);

    // 精确到 cluster 目录，以便跟其他数据源隔离
    static final String aliasesPackage = "com.lgp.dao.**";

    static final String mapperLocations = "classpath:mapper/**/*.xml";

    @Value("${slave.datasource.number}")
    private int dataSourceNumber;

    @Resource(name = "masterDataSource")
    private DataSource masterDataSource;

    @Resource(name = "slaveDataSources")
    private List<DataSource> slaveDataSources;

    @Autowired
    private SqlCostInterceptor sqlCostInterceptor;//sql语句打印拦截器

    @Bean(name = "sqlSessionFactory")
    public SqlSessionFactory sqlSessionFactory() throws Exception {
        logger.info("++++++++ init sqlSessionFactory ++++++++");
        final SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(roundRobinDataSourceProxy());
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        sqlSessionFactoryBean.setMapperLocations(resolver.getResources(mapperLocations));
        sqlSessionFactoryBean.setConfigLocation(resolver.getResource("classpath:mybatis/mybatis-config.xml"));
        //sqlSessionFactoryBean.setPlugins(new Interceptor[]{sqlCostInterceptor});
        return sqlSessionFactoryBean.getObject();
    }

    @Bean(name = "roundRobinDataSourceProxy")
    public AbstractRoutingDataSource roundRobinDataSourceProxy() {
        logger.info("++++++++ init robinDataSourceProxy ++++++++");
        DataSourceRoute proxy = new DataSourceRoute(dataSourceNumber);
        Map<Object, Object> targetDataSources = new HashMap();
        targetDataSources.put(DataSourceType.master.getType(), masterDataSource);
        if(null != slaveDataSources) {
            for(int i=0; i<slaveDataSources.size(); i++){
                targetDataSources.put(i, slaveDataSources.get(i));
            }
        }
        proxy.setDefaultTargetDataSource(masterDataSource);
        proxy.setTargetDataSources(targetDataSources);
        return proxy;
    }

    /**
     * 配置事务管理器
     *
     * @return
     */
    @Bean(name = "transactionManager")
    public DataSourceTransactionManager transactionManagers() {
        logger.info("++++++++ init transactionManager ++++++++");
        return new DataSourceTransactionManager(masterDataSource);
    }
}

