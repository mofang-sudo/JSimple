package com.jsimple.community.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author jinbin
 * @date 2018-07-08 20:40
 */
@Target({ElementType.METHOD, ElementType.TYPE})//定义注解用于修饰的具体目标，（方法、类、接口、枚举类）
@Retention(RetentionPolicy.RUNTIME) //自定义注解时，描述注解的生命周期
public @interface UserLoginToken {
    boolean required() default true;
}
