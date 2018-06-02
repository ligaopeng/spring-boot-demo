package com.lgp.aop;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

/**
 * 类说明
 *
 * @author lgp
 * @create 2018-05-03 20:56
 */
@Aspect//定义切面类
@Component
public class WebLogAspect {

    protected static Logger log = LogManager.getLogger(LogManager.ROOT_LOGGER_NAME);

    ThreadLocal<Long> startTime = new ThreadLocal<>();

    @Pointcut("execution(public * com.lgp.controller.*.*.*(..)) && !execution(public * com.lgp.controller.*.MqController.*(..)) && !execution(public * com.lgp.controller.*.UploadController.*(..))")//定义一个切入点
    public void webLog(){}

    @Before("webLog()")//在切入点开始处切入内容，@After在切入点结尾处切入内容
    public void doBefore(JoinPoint joinPoint) {
        startTime.set(System.currentTimeMillis());
        //接收到请求，记录请求内容
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        Object[] args = joinPoint.getArgs();//直接JSON.toJSONString(joinPoint.getArgs()) 会保错；com.alibaba.fastjson.JSONException: write javaBean error,
        String s = JSON.toJSONString(Arrays.toString(args), SerializerFeature.DisableCircularReferenceDetect,
                SerializerFeature.WriteMapNullValue,
                SerializerFeature.WriteNullStringAsEmpty,
                SerializerFeature.WriteNullListAsEmpty);
        //记录下请求内容
        log.info("++++++++ 3 aspect ++++++++ URL : {}, ARGS : {}, HTTP_METHOD : {}, IP : {}, CLASS_METHOD : {}", request.getRequestURL(),
                s, request.getMethod(), request.getRemoteAddr(), joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());
    }


    @AfterReturning(returning = "obj", pointcut = "webLog()")//在切入点return内容之后切入内容，可以用来处理加工返回值，@Around在切入点前后切入内容，并自己控制何时执行切入点自身的内容，@AfterThrowing用来处理切入内容部分跑出异常之后的处理逻辑
    public void doAfterReturning(Object obj) {
        //处理完请求， 返回内容
        log.info("++++++++ 4 aspect ++++++++ RESPONSE : {}, SPEND TIME : {}", JSON.toJSONString(obj),
                System.currentTimeMillis() - startTime.get());
    }

}
