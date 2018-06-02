package com.lgp.config.websocket.message;

import java.io.Serializable;

/**
 * 浏览器接收的消息
 *
 * @author lgp
 * @create 2018-04-26 16:38
 */
public class WiselyMessage implements Serializable {


    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }



}
