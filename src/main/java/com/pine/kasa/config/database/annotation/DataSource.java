package com.pine.kasa.config.database.annotation;


import java.lang.annotation.*;

/**
 * 多数据源注解
 * @author yp
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DataSource {
    String name() default "";
}
