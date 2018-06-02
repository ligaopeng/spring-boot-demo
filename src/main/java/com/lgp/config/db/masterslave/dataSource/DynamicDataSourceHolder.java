package com.lgp.config.db.masterslave.dataSource;


import com.lgp.config.db.masterslave.enums.DynamicDataSourceGlobal;

/**
 * @Description: 动态数据源持有
 * version V1.0.0
 */
public final class DynamicDataSourceHolder {

    private static final ThreadLocal<DynamicDataSourceGlobal> holder = new ThreadLocal<>();

    private DynamicDataSourceHolder() { }

    public static void putDataSource(DynamicDataSourceGlobal dataSource){
        holder.set(dataSource);
    }

    public static DynamicDataSourceGlobal getDataSource(){
        return holder.get();
    }

    public static void clearDataSource() {
        holder.remove();
    }

}
