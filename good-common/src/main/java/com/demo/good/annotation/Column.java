package com.demo.good.annotation;

import java.lang.annotation.*;

/**
 * 实体类与表之间的转换注解类 create by niyaoping
 */

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Column{
	/**
	 * 	实体类中属性字段对应的 表字段名
	 */
	String name() default "";

	/**
	 * 	表中对应java  中的类型
	 */
	String type() default "String";

	/**
	 * 	是否在查询的时候出现在select语句中，默认是
	 */
	boolean select() default true;

	/**
	 * 	是否作为查询条件出现在where语句中，默认不是
	 */
	boolean where() default false;

	/**
	 * 	是否是自增列，默认不是
	 */
	boolean autoIncrementKey() default false;

	/**
	 * 	是否有默认值，默认不是
	 */
	boolean defaultValue() default false;

	/**
	 * 	是否可以设置字段的值，默认可以
	 */
	boolean setValue() default true;

	/**
	 * 	是否是主键
	 */
	boolean primaryKey() default false;

	/**
	 * 	是否是外键
	 */
	boolean foreignKey() default false;

	/**
	 * 	是否是密文
	 */
	boolean ciphertext() default false;
}
