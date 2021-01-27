package com.yc.database.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import com.yc.database.manager.FieldTypeManager;
/**
 * <pre>
 *     @author yangchong
 *     email  : yangchong211@163.com
 *     time  : 2017/8/6
 *     desc  : Field工具类
 *     revise:
 * </pre>
 */
public class FieldUtil {
	
	/**
	 * 获取任意类型字段的get方法
	 * Author: hyl
	 * Time: 2015-8-16下午10:12:48
	 * @param mClass
	 * @param field
	 * @return
	 */
	public static Method getFieldGetMethod(Class<?> mClass, Field field) {
		if(FieldTypeManager.getFieldType(field) == FieldTypeManager.BASE_TYPE_BOOLEAN) {//boolean类型
			return getBooleanFieldGetMethod(mClass, field);
		}
		try {
			String methodName = "get" + field.getName().substring(0, 1).toUpperCase() + field.getName().substring(1);
			return mClass.getDeclaredMethod(methodName);
		} catch (NoSuchMethodException e) {
			throw new NullPointerException("没有为字段[" + field.getName() + "]按照Java代码规范定义get方法,同时请严格按照驼峰命名法进行变量命名");
		}
	}
	
	/**
	 * 获取Boolean类型字段的get方法
	 * Author: hyl
	 * Time: 2015-8-16下午10:13:01
	 * @param mClass
	 * @param field
	 * @return
	 */
	public static Method getBooleanFieldGetMethod(Class<?> mClass, Field field) {
		try {
			String methodName = "";
			if(isFieldStartWithIs(field.getName())) {
				methodName = field.getName();
			} else {
				methodName = "is" + field.getName().substring(0, 1).toUpperCase() + field.getName().substring(1);
			}
			return mClass.getDeclaredMethod(methodName);
		} catch (NoSuchMethodException e) {
			throw new NullPointerException("没有为字段[" + field.getName() + "]按照Java代码规范定义get方法,同时请严格按照驼峰命名法进行变量命名");
		}
	}
	
	/**
	 * 获取任意类型字段的set方法
	 * Author: hyl
	 * Time: 2015-8-16下午10:13:32
	 * @param mClass
	 * @param field
	 * @return
	 */
	public static Method getFieldSetMethod(Class<?> mClass, Field field) {
		if(FieldTypeManager.getFieldType(field) == FieldTypeManager.BASE_TYPE_BOOLEAN) {//boolean类型
			return getBooleanFieldSetMethod(mClass, field);
		}
		try {
			String methodName = "set" + field.getName().substring(0, 1).toUpperCase() + field.getName().substring(1);
			return mClass.getDeclaredMethod(methodName, field.getType());
		} catch (NoSuchMethodException e) {
			throw new NullPointerException("没有为字段[" + field.getName() + "]按照Java代码规范定义set方法,同时请严格按照驼峰命名法进行变量命名");
		}
	}
	
	/**
	 * 获取Boolean类型字段的set方法
	 * Author: hyl
	 * Time: 2015-8-16下午10:13:43
	 * @param mClass
	 * @param field
	 * @return
	 */
	public static Method getBooleanFieldSetMethod(Class<?> mClass, Field field) {
		try {
			String methodName = "";
			if(isFieldStartWithIs(field.getName())) {
				methodName = "set" + field.getName().substring(2, 3).toUpperCase() + field.getName().substring(3);
			} else {
				methodName = "set" + field.getName().substring(0, 1).toUpperCase() + field.getName().substring(1);
			}
			return mClass.getDeclaredMethod(methodName, field.getType());
		} catch (NoSuchMethodException e) {
			throw new NullPointerException("没有为字段[" + field.getName() + "]按照Java代码规范定义set方法,同时请严格按照驼峰命名法进行变量命名");
		}
	}

	/**
	 * 判断某个字段是否为is开头
	 * Author: hyl
	 * Time: 2015-8-16下午10:13:56
	 * @param fieldName
	 * @return
	 */
	public static boolean isFieldStartWithIs(String fieldName) {
		if(ValueUtil.isEmpty(fieldName) || fieldName.length() < 3) {
			return false;
		}
		//必须以 is 开头，并且is之后的第一个字母为大写,比如：isAuto,该字段的get、set分别为：isAuto,setAuto
		if(fieldName.startsWith("is") && Character.isUpperCase(fieldName.charAt(2))) {
			return true;
		}
		return false;
	}
}
