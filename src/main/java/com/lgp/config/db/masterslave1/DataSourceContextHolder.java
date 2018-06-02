package com.lgp.config.db.masterslave1;

/**
 * 类说明
 * threadLocal保存数据源类型
 * @author lgp
 * @create 2018-05-09 17:08
 */
public class DataSourceContextHolder {

    private static final ThreadLocal<String> local = new ThreadLocal<>();

    public static ThreadLocal<String> getLocal() {
        return local;
    }

    public static void slave() {
        local.set(DataSourceType.slave.getType());
    }

    public static void master() {
        local.set(DataSourceType.master.getType());
    }

    public static String getJdbcType() {
        return local.get();
    }

    public static void clearDataSource(){
        local.remove();
    }

}
