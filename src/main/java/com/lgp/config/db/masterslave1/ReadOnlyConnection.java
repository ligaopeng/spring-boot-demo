package com.lgp.config.db.masterslave1;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 类说明
 * 只读属性注解， 在service方法上使用
 * @author lgp
 * @create 2018-05-09 21:44
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ReadOnlyConnection {

}
