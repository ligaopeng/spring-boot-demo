package com.lgp.autoconfigure;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 类说明
 *
 * @author lgp
 * @create 2018-04-24 20:58
 */
@ConfigurationProperties(prefix = "hello")
public class HelloServiceProperties {

    private static final String MSG = "world";

    private String msg = MSG;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
