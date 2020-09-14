package com.demo.good.annotation;

import java.lang.annotation.*;

/**
 * 自定义注解，用于表示类名与数据库表名之间的映射
 * Create  By  Yaoping.NI
  */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Table{
	String value() default "";
}
