package com.yigos.framework.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 自定义的注解类型，用于填写字段的中文说明
 * @author kavin
 */
@Target({ ElementType.TYPE, ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface FieldComment {
    //名称
    String name() default "";
    //类型
    String type() default "";
    // 格式
    String format() default "";
    //值
    String value() default "";

}