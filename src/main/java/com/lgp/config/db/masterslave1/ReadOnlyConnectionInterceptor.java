package com.lgp.config.db.masterslave1;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

/**
 * 类说明
 *
 * @author lgp
 * @create 2018-05-09 21:47
 */
@Aspect
@Component
public class ReadOnlyConnectionInterceptor implements Ordered {

    protected static Logger logger = LogManager.getLogger(LogManager.ROOT_LOGGER_NAME);

    @Override
    public int getOrder() {
        return 0;
    }

    @Around("@annotation(readOnlyConnection)")
    public Object proceed(ProceedingJoinPoint proceedingJoinPoint, ReadOnlyConnection readOnlyConnection) throws Throwable{
        try {
            logger.info("set database connection to read only");
            DataSourceContextHolder.slave();
            Object result = proceedingJoinPoint.proceed();
            return result;
        } finally {
            DataSourceContextHolder.clearDataSource();
            logger.info("restore database connection");
        }
    }
}
