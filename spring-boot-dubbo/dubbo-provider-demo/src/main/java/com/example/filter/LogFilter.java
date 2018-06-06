package com.example.filter;

import com.alibaba.dubbo.rpc.*;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;

/**
 * 类说明
 *
 * @author lgp
 * @create 2018-06-06 15:06
 */
@Slf4j
public class LogFilter implements Filter {


    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        Object[] arguments = invocation.getArguments();
        log.info("request body {}", JSON.toJSONString(arguments));
        Result result = invoker.invoke(invocation);
        log.info("result {}", JSON.toJSONString(result.getValue()));
        return result;
    }
}
