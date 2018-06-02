package com.lgp.exception;

/**
 * 类说明
 *
 * @author lgp
 * @create 2018-04-30 21:25
 */
public class BaseException extends RuntimeException {

    private String code;

    private String msg;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
