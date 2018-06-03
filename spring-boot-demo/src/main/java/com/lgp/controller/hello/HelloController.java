package com.lgp.controller.hello;

import com.lgp.autoconfigure.HelloService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 测试自动配置
 *
 * @author lgp
 * @create 2018-04-24 21:22
 */
@RestController
@RequestMapping(value = "hello")
public class HelloController {

    protected static Logger log = LogManager.getLogger(LogManager.ROOT_LOGGER_NAME);

    @Autowired
    HelloService helloService;

    @GetMapping(value = "getMsg")
    public String getHelloMsg() {
        String msg = helloService.getMsg();
        log.info(msg + "===========");
        return msg;
    }
}
