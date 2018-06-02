package com.lgp.config.db.masterslave.dataSource;

import com.lgp.config.db.masterslave.enums.DynamicDataSourceGlobal;
import com.lgp.config.db.masterslave.strategy.RoundRobinStrategy;
import com.lgp.config.db.masterslave.strategy.Strategy;
import org.springframework.beans.BeanUtils;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description: 动态数据源实现读写分离
 * version V1.0.0
 */
public class DynamicDataSource extends AbstractRoutingDataSource {

    private DataSource masterDataSource; //写数据源
    private List<DataSource> slavesDataSource = new ArrayList<>(); //读数据源
    private Class<? extends Strategy> strategyClass = RoundRobinStrategy.class; //获取数据源的策略
    private Strategy strategy; //策略

    @Override
    public void afterPropertiesSet() {
        if (this.masterDataSource == null) {
            throw new IllegalArgumentException("Property 'writeDataSource' is required");
        }
        setDefaultTargetDataSource(masterDataSource);
        Map<Object, Object> targetDataSources = new HashMap<Object, Object>();
        targetDataSources.put(DynamicDataSourceGlobal.WRITE.name(), masterDataSource);
        if (slavesDataSource != null && slavesDataSource.size() > 0) {
            for (int i = 0; i < slavesDataSource.size(); i++) {
                targetDataSources.put(DynamicDataSourceGlobal.READ.name() + "_" + i, slavesDataSource.get(i));
            }
        }
        // 设置数据源
        setTargetDataSources(targetDataSources);
        super.afterPropertiesSet();
    }

    /**
      * @Description: 每次去连数据库的时候，spring会调用这个方法去找对应的数据源。返回值即对应的数据源的LookUpKey.
      * @Title determineCurrentLookupKey
      * @return DataSource
      * @throws
      */
    @Override
    protected Object determineCurrentLookupKey() {

        DynamicDataSourceGlobal dynamicDataSourceGlobal = DynamicDataSourceHolder.getDataSource();

        if (dynamicDataSourceGlobal == null
                || dynamicDataSourceGlobal == DynamicDataSourceGlobal.WRITE) {
            return DynamicDataSourceGlobal.WRITE.name();
        }
        // 为读库的时候取数据源
        String dataSource = getSlaveDataSource();
        return dataSource;
    }

    /**
     * @Description: 获取从库
     * @Title getSlaveDataSource
     * @return javax.sql.DataSource
     * @throws
     */
    public String getSlaveDataSource() {

        if( null == strategy ){
            strategy = BeanUtils.instantiateClass(strategyClass);
        }
        return strategy.select(slavesDataSource, masterDataSource);
    }

    public DataSource getMasterDataSource() {
        return masterDataSource;
    }

    public void setMasterDataSource(DataSource masterDataSource) {
        this.masterDataSource = masterDataSource;
    }

    public List<DataSource> getSlavesDataSource() {
        return slavesDataSource;
    }

    public void setSlavesDataSource(List<DataSource> slavesDataSource) {
        this.slavesDataSource = slavesDataSource;
    }

    public void setReadDataSource(DataSource readDataSource){
        this.slavesDataSource.add(readDataSource);
    }

    public Class<? extends Strategy> getStrategyClass() {
        return strategyClass;
    }

    public void setStrategyClass(Class<? extends Strategy> strategyClass) {
        this.strategyClass = strategyClass;
    }

    public Strategy getStrategy() {
        return strategy;
    }
}
