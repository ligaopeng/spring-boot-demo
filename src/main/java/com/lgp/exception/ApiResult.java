package com.lgp.exception;

import java.io.Serializable;

/**
 * 类说明
 *
 * @author lgp
 * @create 2018-04-30 21:02
 */
public class ApiResult implements Serializable {

    public ApiResult(){}

    public static ApiResult newInstance(){
        return new ApiResult();
    }

    private Boolean flag = Boolean.TRUE;

    private String msg;

    private Object result;

    //查询出的结构总数
    private int rows;

    //需要跳转的URL
    private String jumpUrl;

    //接口响应时间毫秒
    private String dateTime;

    public Boolean getFlag() {
        return flag;
    }

    public void setFlag(Boolean flag) {
        this.flag = flag;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public String getJumpUrl() {
        return jumpUrl;
    }

    public void setJumpUrl(String jumpUrl) {
        this.jumpUrl = jumpUrl;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }
}
