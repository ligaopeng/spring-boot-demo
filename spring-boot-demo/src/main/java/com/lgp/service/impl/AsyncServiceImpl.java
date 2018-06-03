package com.lgp.service.impl;

import com.lgp.service.AsyncService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.concurrent.Future;

/**
 * 类说明
 *
 * @author lgp
 * @create 2018-05-12 17:56
 */
@Service
@Slf4j
public class AsyncServiceImpl implements AsyncService {

    private static Random random = new Random();

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    @Async
    @Override
    public void doTaskOne() throws Exception {
        log.info("开始做任务一");
        long start = System.currentTimeMillis();
        log.info("任务一 : {}", stringRedisTemplate.randomKey());
        long end = System.currentTimeMillis();
        log.info("完成任务一，耗时：" + (end - start) + "毫秒");
    }

    @Async
    @Override
    public void doTaskTwo() throws Exception {
        log.info("开始做任务二");
        long start = System.currentTimeMillis();
        log.info("任务二 : {}", stringRedisTemplate.randomKey());
        long end = System.currentTimeMillis();
        log.info("完成任务二，耗时：" + (end - start) + "毫秒");
    }

    @Async
    @Override
    public void doTaskThree() throws Exception {
        log.info("开始做任务三");
        long start = System.currentTimeMillis();
        log.info("任务三 : {}", stringRedisTemplate.randomKey());
        long end = System.currentTimeMillis();
        log.info("完成任务三，耗时：" + (end - start) + "毫秒");
    }

    @Async("taskExecutor")
    @Override
    public Future<String> doTaskFour() throws Exception {
        log.info("开始做任务四");
        long start = System.currentTimeMillis();
        log.info("任务四 : {}", stringRedisTemplate.randomKey());
        long end = System.currentTimeMillis();
        log.info("完成任务四，耗时：" + (end - start) + "毫秒");
        return new AsyncResult<>("任务四完成");
    }

    @Async("taskExecutor")
    @Override
    public Future<String> doTaskFive() throws Exception {
        log.info("开始做任务五");
        long start = System.currentTimeMillis();
        log.info("任务五 : {}", stringRedisTemplate.randomKey());
        long end = System.currentTimeMillis();
        log.info("完成任务五，耗时：" + (end - start) + "毫秒");
        return new AsyncResult<>("任务五完成");
    }

    @Async("taskExecutor")
    @Override
    public Future<String> doTaskSix() throws Exception {
        log.info("开始做任务六");
        long start = System.currentTimeMillis();
        log.info("任务六 : {}", stringRedisTemplate.randomKey());
        long end = System.currentTimeMillis();
        log.info("完成任务六，耗时：" + (end - start) + "毫秒");
        return new AsyncResult<>("任务六完成");
    }
}
