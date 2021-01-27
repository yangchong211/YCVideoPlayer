package com.yc.database.manager;

import java.lang.reflect.Field;
import java.util.Date;


/**
 * <pre>
 *     @author yangchong
 *     email  : yangchong211@163.com
 *     time  : 2017/8/6
 *     desc  : 字段类型
 *     revise:
 * </pre>
 */
public final class FieldTypeManager {
	/**
	 * 空值
	 */
	public static final int VALUE_TYPE_NULL = -1;
	/**
	 * 非数据库支持基本类型
	 */
	public static final int NOT_BASE_TYPE = 0;
	/**
	 * String
	 */
	public static final int BASE_TYPE_STRING = 1;
	/**
	 * int
	 */
	public static final int BASE_TYPE_INT = 2;
	/**
	 * short
	 */
	public static final int BASE_TYPE_SHORT = 3;
	/**
	 * float
	 */
	public static final int BASE_TYPE_FLOAT = 4;
	/**
	 * double
	 */
	public static final int BASE_TYPE_DOUBLE = 5;
	/**
	 * long
	 */
	public static final int BASE_TYPE_LONG = 6;
	/**
	 * char
	 */
	public static final int BASE_TYPE_CHAR = 7;
	/**
	 * boolean
	 */
	public static final int BASE_TYPE_BOOLEAN = 8;
	/**
	 * byte[]
	 */
	public static final int BASE_TYPE_BYTE_ARRAY = 9;
	/**
	 * Date
	 */
	public static final int BASE_TYPE_DATE = 10;
	
	/**
	 * 该字段是否为基本字段类型
	 * @param field
	 * @return
	 */
	public static int getFieldType(Field field) {
		Class<?> fieldType = field.getType();
		if (fieldType == String.class) {
			return FieldTypeManager.BASE_TYPE_STRING;
		}
		if(fieldType == Date.class || fieldType == java.sql.Date.class) {
			return FieldTypeManager.BASE_TYPE_DATE;
		}
		if (fieldType == boolean.class || fieldType == Boolean.class) {
			return FieldTypeManager.BASE_TYPE_BOOLEAN;
		}
		if (fieldType == short.class || fieldType == Short.class) {
			return FieldTypeManager.BASE_TYPE_SHORT;
		} 
		if (fieldType == int.class || fieldType == Integer.class) {
			return FieldTypeManager.BASE_TYPE_INT;
		}
		if (fieldType == long.class || fieldType == Long.class) {
			return FieldTypeManager.BASE_TYPE_LONG;
		}
		if (fieldType == float.class || fieldType == Float.class) {
			return FieldTypeManager.BASE_TYPE_FLOAT;
		}
		if (fieldType == double.class || fieldType == Double.class) {
			return FieldTypeManager.BASE_TYPE_DOUBLE;
		}
		if (fieldType == byte[].class || fieldType == Byte[].class) {
			return FieldTypeManager.BASE_TYPE_BYTE_ARRAY;
		}
		if(fieldType == char.class || fieldType == Character.class) {
			return FieldTypeManager.BASE_TYPE_CHAR;
		}
		return FieldTypeManager.NOT_BASE_TYPE;
	}
	
	/**
	 * 获取某个值的类型
	 * @param obj
	 * @return
	 */
	public static int getValueType(Object obj) {
		if(obj == null) {
			return FieldTypeManager.VALUE_TYPE_NULL;
		}
		if (obj instanceof String) {
			return FieldTypeManager.BASE_TYPE_STRING;
		}
		if(obj instanceof Date || obj instanceof java.sql.Date) {
			return FieldTypeManager.BASE_TYPE_DATE;
		}
		if (obj instanceof Boolean) {
			return FieldTypeManager.BASE_TYPE_BOOLEAN;
		}
		if (obj instanceof Short) {
			return FieldTypeManager.BASE_TYPE_SHORT;
		} 
		if (obj instanceof Integer) {
			return FieldTypeManager.BASE_TYPE_INT;
		}
		if (obj instanceof Long) {
			return FieldTypeManager.BASE_TYPE_LONG;
		}
		if (obj instanceof Float) {
			return FieldTypeManager.BASE_TYPE_FLOAT;
		}
		if (obj instanceof Double) {
			return FieldTypeManager.BASE_TYPE_DOUBLE;
		}
		if (obj instanceof byte[] || obj instanceof Byte[]) {
			return FieldTypeManager.BASE_TYPE_BYTE_ARRAY;
		}
		if(obj instanceof Character) {
			return FieldTypeManager.BASE_TYPE_CHAR;
		}
		return FieldTypeManager.NOT_BASE_TYPE;
	}
	
	/**
	 * 根据字段类型获取数据库支持的字段类型
	 * @param field
	 * @return
	 */
	public static String getColumnTypeValue(Field field) {
		String type = "TEXT";
		int fieldType = FieldTypeManager.getFieldType(field);
		switch(fieldType) {
			case FieldTypeManager.BASE_TYPE_CHAR:
			case FieldTypeManager.BASE_TYPE_STRING:
			case FieldTypeManager.BASE_TYPE_DATE:
				type = "TEXT";
				break;
			case FieldTypeManager.BASE_TYPE_BOOLEAN:
			case FieldTypeManager.BASE_TYPE_INT:
			case FieldTypeManager.BASE_TYPE_SHORT:
			case FieldTypeManager.BASE_TYPE_LONG:
				type = "INTEGER";
				break;
			case FieldTypeManager.BASE_TYPE_DOUBLE:
			case FieldTypeManager.BASE_TYPE_FLOAT:
				type = "REAL";
				break;
			case FieldTypeManager.BASE_TYPE_BYTE_ARRAY:
				type = "BLOB";
				break;
		}
		return type;
	}
	
	/**
	 * 当前字段是否为自增长字段类型
	 * @param field
	 * @return
	 */
	public static boolean isAutoIncrementType(Field field) {
		int type = getFieldType(field);
		if(type != BASE_TYPE_LONG) {
			return false;
		}
		return true;
	}
}
