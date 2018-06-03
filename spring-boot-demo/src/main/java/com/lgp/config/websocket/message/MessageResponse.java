package com.lgp.config.websocket.message;

import java.io.Serializable;

/**
 * 服务端发出的消息
 *
 * @author lgp
 * @create 2018-04-26 16:45
 */
public class MessageResponse implements Serializable {

    private String responseMessage;

    public MessageResponse(String responseMessage) {
        this.responseMessage = responseMessage;
    }

    public String getResponseMessage() {
        return responseMessage;
    }

    public void setResponseMessage(String responseMessage) {
        this.responseMessage = responseMessage;
    }
}
