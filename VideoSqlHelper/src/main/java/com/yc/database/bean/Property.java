package com.yc.database.bean;

import android.database.Cursor;

import java.lang.reflect.Field;
import java.util.Date;

import com.yc.database.manager.FieldTypeManager;
import com.yc.database.utils.DateUtil;
import com.yc.database.utils.ValueUtil;


/**
 * <pre>
 *     @author yangchong
 *     email  : yangchong211@163.com
 *     time  : 2017/8/6
 *     desc  : 实体属性字段
 *     revise:
 * </pre>
 */
public class Property {
	/**
	 * 字段名，建表时用
	 */
	private String column;
	/**
	 * 默认值，建表时要设置的字段默认值
	 */
	private String defaultValue;
	/**
	 * 该字段对应实体信息中的属性字段信息
	 */
	private Field field;
	
	/**
	 * 获取指定对象的当前字段的值
	 * Author: hyl
	 * Time: 2015-8-16下午10:23:35
	 * @param entity	获取字段值的对象
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public <T> T getValue(Object entity) {
		if(entity != null) {
			try {
				field.setAccessible(true);
				return (T) field.get(entity);
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	
	/**
	 * 设置指定对象的当前字段的值
	 * Author: hyl
	 * Time: 2015-8-16下午10:24:08
	 * @param entity	要设置字段值的对象
	 * @param value		要设置的值
	 * @throws Exception
	 */
	public void setValue(Object entity, Object value) throws Exception {
		int fieldType = FieldTypeManager.getFieldType(field);
		try {
			field.setAccessible(true);
			switch(fieldType) {
				case FieldTypeManager.BASE_TYPE_BOOLEAN:
					field.set(entity, Boolean.parseBoolean(value.toString()));
					break;
				case FieldTypeManager.BASE_TYPE_BYTE_ARRAY:
					field.set(entity, (byte[])value);
					break;
				case FieldTypeManager.BASE_TYPE_CHAR:
					field.set(entity, value.toString().charAt(0));
					break;
				case FieldTypeManager.BASE_TYPE_STRING:
					field.set(entity, value.toString());
					break;
				case FieldTypeManager.BASE_TYPE_DATE:
					field.set(entity, DateUtil.formatDatetime((Date) value));
					break;
				case FieldTypeManager.BASE_TYPE_DOUBLE:
					field.set(entity, Double.parseDouble(value.toString()));
					break;
				case FieldTypeManager.BASE_TYPE_FLOAT:
					field.set(entity, Float.parseFloat(value.toString()));
					break;
				case FieldTypeManager.BASE_TYPE_INT:
					field.set(entity, Integer.parseInt(value.toString()));
					break;
				case FieldTypeManager.BASE_TYPE_LONG:
					field.set(entity, Long.parseLong(value.toString()));
					break;
				case FieldTypeManager.BASE_TYPE_SHORT:
					field.set(entity, Short.parseShort(value.toString()));
					break;
			}
		} catch (Exception e) {
			throw e;
		}
	}
	
	/**
	 * 设置指定实体对象当前属性字段的值
	 * Author: hyl
	 * Time: 2015-8-21上午10:20:14
	 * @param entity		要设置值的实体对象
	 * @param cursor		数据来源
	 * @throws Exception
	 */
	public void setValue(Object entity, Cursor cursor) throws Exception {
		int fieldType = FieldTypeManager.getFieldType(field);
		try {
			int columnIdx = cursor.getColumnIndex(column);
			if(columnIdx == -1) {//当前游标中没有该字段的值
				return;
			}
			String columnValue = cursor.getString(columnIdx);
			boolean isEmpty = ValueUtil.isEmpty(columnValue);
			field.setAccessible(true);
			switch(fieldType) {
				case FieldTypeManager.BASE_TYPE_BOOLEAN:
					field.set(entity, isEmpty ? false : Boolean.parseBoolean(columnValue));
					break;
				case FieldTypeManager.BASE_TYPE_BYTE_ARRAY:
					field.set(entity, cursor.getBlob(columnIdx));
					break;
				case FieldTypeManager.BASE_TYPE_CHAR:
					field.set(entity, isEmpty ? Character.valueOf(' ') : columnValue.charAt(0));
					break;
				case FieldTypeManager.BASE_TYPE_STRING:
					field.set(entity, isEmpty ? "" : columnValue);
					break;
				case FieldTypeManager.BASE_TYPE_DATE:
					field.set(entity, isEmpty ? "" : DateUtil.parseDatetime(columnValue));
					break;
				case FieldTypeManager.BASE_TYPE_DOUBLE:
					field.set(entity, cursor.getDouble(columnIdx));
					break;
				case FieldTypeManager.BASE_TYPE_FLOAT:
					field.set(entity, cursor.getFloat(columnIdx));
					break;
				case FieldTypeManager.BASE_TYPE_INT:
					field.set(entity, cursor.getInt(columnIdx));
					break;
				case FieldTypeManager.BASE_TYPE_LONG:
					field.set(entity, cursor.getLong(columnIdx));
					break;
				case FieldTypeManager.BASE_TYPE_SHORT:
					field.set(entity, cursor.getShort(columnIdx));
					break;
			}
		} catch (Exception e) {
			throw e;
		}
	}
	
	public String getColumn() {
		return column;
	}
	
	public void setColumn(String column) {
		this.column = column;
	}
	
	public String getDefaultValue() {
		return defaultValue;
	}
	
	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}
	
	public Field getField() {
		return field;
	}
	
	public void setField(Field field) {
		this.field = field;
	}
}
