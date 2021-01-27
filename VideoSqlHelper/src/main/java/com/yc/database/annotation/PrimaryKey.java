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
 *     desc  : 主键配置，必须配置该注解，不配置columnName则默认以字段名作为列名，autoGenerate表示主键是否为自增长，默认为是
 *     revise:
 * </pre>
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface PrimaryKey {

	/**
	 * 配置该字段映射到数据库中的列名，不配置默认为字段名
	 * @return
	 */
	String columnName() default "";
	/**
	 * 该主键是否设置为自增长，默认为否
	 * @return
	 */
	boolean isAutoGenerate() default false;

}
