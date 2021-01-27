package com.yc.database.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * <pre>
 *     @author yangchong
 *     email  : yangchong211@163.com
 *     time  : 2017/8/6
 *     desc  : 实体字段不设置该注解，则默认已字段名作为列名
 *     revise: 此注解配置数据库指定列名和字段默认值，不配置columnName则默认以字段名作为列名，不配置defaultValue则无列默认值
 * </pre>
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Column {

	/**
	 * 配置该字段映射到数据库中的列名，不配置默认为字段名
	 * @return
	 */
	String columnName() default "";
	/**
	 * 字段默认值
	 * @return
	 */
	String defaultValue() default "";

}
