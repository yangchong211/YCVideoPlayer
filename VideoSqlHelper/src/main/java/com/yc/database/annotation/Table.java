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
 *     desc  : 表名注解，实体不设置此注解，或者设置了此注解但name不设置则默认以实体类名作为表名
 *     revise:
 * </pre>
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Table {

	/**
	 * 自定义表名
	 * @return
	 */
	String name() default "";

}
