package com.lgp.mq.rocketmq;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;

/**
 * 类说明
 *
 * @author lgp
 * @create 2018-06-02 13:58
 */
@Slf4j
public class MySendCallBack implements SendCallback {

    public void onSuccess(final SendResult sendResult) {
        log.info("SendCallback-发送消息成功! sendResult :{}", sendResult.toString());
    }

    public void onException(final Throwable e) {
        log.info("SendCallback-发送消息失败! 异常 :{}", e.toString());
    }
}
