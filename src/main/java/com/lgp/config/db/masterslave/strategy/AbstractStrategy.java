package com.lgp.config.db.masterslave.strategy;


import com.lgp.config.db.masterslave.enums.DynamicDataSourceGlobal;

import javax.sql.DataSource;

/**
 * @Description: 负载均衡策略
 * version V1.0.0
 */
public abstract class AbstractStrategy implements Strategy {

    public String select(java.util.List<DataSource> slaves, DataSource master) {
        if (slaves == null || slaves.isEmpty())
            return DynamicDataSourceGlobal.WRITE.name();
        if (slaves.size() == 1)
            return DynamicDataSourceGlobal.READ.name() + "_0";
        return doSelect(slaves,master);
    }

    /** 
      * @Description: 读的数据源为多个的时候
      * @param  slaves, master
      * @return String
      * @throws 
      */
    protected abstract String doSelect(java.util.List<DataSource> slaves, DataSource master);
}