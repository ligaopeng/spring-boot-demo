package com.lgp.repository.customer;

import java.io.Serializable;

/**
 * 类说明
 *
 * @author lgp
 * @create 2018-04-30 15:35
 */
public class Customer implements Serializable {

    private Integer id;

    private String loginName;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }
}
