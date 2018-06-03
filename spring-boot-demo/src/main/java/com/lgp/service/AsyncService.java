package com.lgp.service;

import java.util.concurrent.Future;

/**
 * 类说明
 *
 * @author lgp
 * @create 2018-05-12 17:55
 */
public interface AsyncService {

    void doTaskOne() throws Exception;

    void doTaskTwo() throws Exception;

    void doTaskThree() throws Exception;

    Future<String> doTaskFour() throws Exception;

    Future<String> doTaskFive() throws Exception;

    Future<String> doTaskSix() throws Exception;
}
