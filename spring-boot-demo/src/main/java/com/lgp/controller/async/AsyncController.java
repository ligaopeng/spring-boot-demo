package com.lgp.controller.async;

import com.lgp.service.AsyncService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.Future;

/**
 * 类说明
 *
 * @author lgp
 * @create 2018-05-12 17:48
 */
@Slf4j
@RestController
@RequestMapping(value = "async")
public class AsyncController {

    @Autowired
    AsyncService asyncService;

    /**
     * 异步调用
     *
     * @throws Exception
     */
    @GetMapping(value = "asyncMethods")
    public void asyncMethods() throws Exception {
        long start = System.currentTimeMillis();
        asyncService.doTaskOne();//同步调用
        asyncService.doTaskTwo();//异步调用
        asyncService.doTaskThree();//异步调用
        long end = System.currentTimeMillis();
        log.info("任务全部完成，总耗时：" + (end - start) + "毫秒");

    }

    /**
     * 异步回调
     *
     * @throws Exception
     */
    @GetMapping(value = "asyncMethodsReturn")
    public void asyncMethodsReturn() throws Exception {
        long start = System.currentTimeMillis();
        Future<String> doTaskFour = asyncService.doTaskFour();//异步回调
        Future<String> doTaskFive = asyncService.doTaskFive();//异步回调
        Future<String> doTaskSix = asyncService.doTaskSix();//异步回调
        while (true) {
            if (doTaskFour.isDone() && doTaskFive.isDone() && doTaskSix.isDone()) {
                // 三个任务都调用完成，退出循环等待
                break;
            }
            //Thread.sleep(1000);
        }
        long end = System.currentTimeMillis();
        log.info("任务全部完成，总耗时：" + (end - start) + "毫秒");
    }

    /**
     * 异步回调
     *
     * @throws Exception
     */
    @GetMapping(value = "asyncPoolShutDown")
    public void asyncPoolShutDown() throws Exception {
        long start = System.currentTimeMillis();
        for (int i = 0; i < 10000; i++) {
            asyncService.doTaskOne();//异步调用
            asyncService.doTaskTwo();//异步调用
            asyncService.doTaskThree();//异步调用
            log.info("+++++++++++++++++++++++ " + i + " +++++++++++++++++++++++++++++++++");
            if (i == 9999) {
                System.exit(0);
            }
        }
        long end = System.currentTimeMillis();
        log.info("任务全部完成，总耗时：" + (end - start) + "毫秒");
    }


}
