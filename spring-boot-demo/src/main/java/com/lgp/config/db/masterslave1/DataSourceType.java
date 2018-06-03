package com.lgp.config.db.masterslave1;

/**
 * 类说明
 * 定义数据源类型
 *
 * @author lgp
 * @create 2018-05-09 17:07
 */
public enum DataSourceType {

    master("master", "master"), slave("slave", "slave");

    private String type;

    private String name;

    DataSourceType(String type, String name) {
        this.type = type;
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
