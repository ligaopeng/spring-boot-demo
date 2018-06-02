package com.lgp.config.redis;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.concurrent.CountDownLatch;

/**
 * 类说明
 *
 * @author lgp
 * @create 2018-05-09 13:43
 */
public class Receiver {

    private CountDownLatch latch;

    @Autowired
    public Receiver(CountDownLatch latch) {
        this.latch = latch;
    }

    public void receiverMessage(String message) {
        System.out.println("Received : <" + message + ">");
        latch.countDown();
    }
}
