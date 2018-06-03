package com.lgp.interceptor;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.lgp.common.util.LoggerUtils;
import com.lgp.entity.logger.LoggerEntity;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 类说明
 *
 * @author lgp
 * @create 2018-05-03 22:10
 */
public class WebLogInterceptor implements HandlerInterceptor {

    protected static Logger log = LogManager.getLogger(LogManager.ROOT_LOGGER_NAME);

    //请求开始时间标识
    private static final String LOGGER_SEND_TIME = "_send_time";

    //请求日志实体标识
    private static final String LOGGER_ENTITY = "_logger_entity";

    /**
     * 进入SpringMVC的Controller之前开始记录日志实体
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //创建日志实体
        LoggerEntity loggerEntity = new LoggerEntity();
        //获取请求sessionId
        String sessionId = request.getRequestedSessionId();
        //请求路径url
        String url = request.getRequestURL().toString();
        //获取请求参数信息
        String paramData = JSON.toJSONString(request.getParameterMap(),
                SerializerFeature.DisableCircularReferenceDetect,
                SerializerFeature.WriteMapNullValue,
                SerializerFeature.WriteNullStringAsEmpty,
                SerializerFeature.WriteNullListAsEmpty);
        //获得客户端IP
        loggerEntity.setClientIp(LoggerUtils.getClientIp(request));
        //设置请求方法
        loggerEntity.setMethod(request.getMethod());
        //设置请求类型
        loggerEntity.setType(LoggerUtils.getRequestType(request));
        //设置请求参数内容
        loggerEntity.setParamData(paramData);
        //设置请求URL
        loggerEntity.setUri(url);
        //设置SessionID
        loggerEntity.setSessionId(sessionId);
        //设置请求开始时间
        request.setAttribute(LOGGER_SEND_TIME, System.currentTimeMillis());
        //设置请求实体到request内，方面afterCompletion方法调用
        request.setAttribute(LOGGER_ENTITY, loggerEntity);
        log.info("++++++++ 2 interceptor ++++++++ request info : {}", JSON.toJSONString(loggerEntity));
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        //获取请求错误码
        int status = response.getStatus();
        //当前时间
        long endTime = System.currentTimeMillis();
        //请求开始时间
        long startTime = Long.valueOf(request.getAttribute(LOGGER_SEND_TIME).toString());
        //获取本次请求日志实体
        LoggerEntity loggerEntity = (LoggerEntity) request.getAttribute(LOGGER_ENTITY);
        //设置时间差
        loggerEntity.setTimeConsuming(Integer.valueOf((endTime - startTime) + ""));
        //设置返回时间
        loggerEntity.setReturnTime(endTime + "");
        //设置错误码
        loggerEntity.setHttpStatusCode(status + "");
        //设置返回值
        loggerEntity.setReturnData(JSON.toJSONString(request.getAttribute(LoggerUtils.LOGGER_RETURN),
                SerializerFeature.DisableCircularReferenceDetect,
                SerializerFeature.WriteMapNullValue,
                SerializerFeature.WriteNullStringAsEmpty,
                SerializerFeature.WriteNullListAsEmpty));
        //LoggerService loggerService = getService(LoggerService.class, request);
        //loggerService.saveLogger(loggerEntity);
        log.info("++++++++ 5 interceptor ++++++++ request info : {}", JSON.toJSONString(loggerEntity));
    }

    /**
     * 根据传入的类型获取spring管理的对应dao
     *
     * @param clazz   类型
     * @param request 请求对象
     * @param <T>
     * @return
     */
    private <T> T getService(Class<T> clazz, HttpServletRequest request) {
        BeanFactory factory = WebApplicationContextUtils.getRequiredWebApplicationContext(request.getServletContext());
        return factory.getBean(clazz);
    }
}
