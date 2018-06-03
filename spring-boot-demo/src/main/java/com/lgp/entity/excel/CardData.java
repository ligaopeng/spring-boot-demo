package com.lgp.entity.excel;

import cn.afterturn.easypoi.excel.annotation.Excel;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 类说明
 *
 * @author lgp
 * @create 2018-05-18 11:13
 */
public class CardData implements Serializable {

    @Excel(name = "user_phone")
    @NotNull
    private String userPhone;

    @Excel(name = "open_time")
    @NotNull
    private String openTime;

    @Excel(name = "expire_time")
    @NotNull
    private String expireTime;

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getOpenTime() {
        return openTime;
    }

    public void setOpenTime(String openTime) {
        this.openTime = openTime;
    }

    public String getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(String expireTime) {
        this.expireTime = expireTime;
    }
}
