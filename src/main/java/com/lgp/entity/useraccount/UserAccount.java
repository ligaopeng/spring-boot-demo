package com.lgp.entity.useraccount;

import java.io.Serializable;

/**
 * 类说明
 *
 * @author lgp
 * @create 2018-04-27 22:59
 */
public class UserAccount implements Serializable {

    private Integer id;

    private String userName;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
