package com.lgp.controller.message;

import com.lgp.config.websocket.message.MessageResponse;
import com.lgp.config.websocket.message.WiselyMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

/**
 * 类说明
 *
 * @author lgp
 * @create 2018-04-26 16:47
 */
@RestController
@MessageMapping(value = "message")
public class MessageController {

    @Autowired
    SimpMessagingTemplate simpMessagingTemplate; //通过SimpMessagingTemplate向浏览器发消息
    /**
     * 广播式 WebSocket
     * @param wiselyMessage
     * @return
     * @throws Exception
     */

    @MessageMapping(value = "say")//浏览器会通过这个地址映射，相当于@RequesrMapping
    @SendTo(value = "/topic/getResponse")//当服务端有消息时，会对订阅了@SendTo中的路径的浏览器发送消息
    public MessageResponse say(WiselyMessage wiselyMessage) throws Exception{
        Thread.sleep(3000);
        return new MessageResponse("welcome " + wiselyMessage.getMessage());
    }


    @MessageMapping(value = "chat")
    public void handleChat(Principal principal, String msg) {//Principal包含用户的信息
        if (principal.getName().equals("LGP")) {
            simpMessagingTemplate.convertAndSendToUser("LI", "/queue/notifications",
                    principal.getName() + "-send: " + msg);//第一个参数是接收消息的用户，第二个参数是浏览器订阅的地址，第三个是消息本身
        } else {
            simpMessagingTemplate.convertAndSendToUser("LGP", "/queue/notifications",
                    principal.getName() + "-send: " + msg);
        }
    }


}
