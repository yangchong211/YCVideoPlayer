package com.yc.database.utils;

import android.database.Cursor;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import com.yc.database.bean.EntityTable;
import com.yc.database.bean.Property;
import com.yc.database.manager.EntityTableManager;


/**
 * <pre>
 *     @author yangchong
 *     email  : yangchong211@163.com
 *     time  : 2017/8/6
 *     desc  : Cursor工具类
 *     revise:
 * </pre>
 */
public class CursorUtil {
	/**
	 * 判断Cursor是否正确，即存在结果集
	 * @param cursor
	 * @return
	 */
	public static boolean isCursorRight(Cursor cursor) {
		if(cursor == null || cursor.getCount() <= 0) {
			return false;
		}
		return true;
	}
	
	/**
	 * 关闭某个Cursor
	 * @param cursor
	 */
	public static void closeCursor(Cursor cursor) {
		if(cursor != null && !cursor.isClosed()) {
			cursor.close();
		}
		cursor = null;
	}

	/**
	 * 解析查询游标结果集为指定实体类列表(解析完成之后会关闭游标)
	 * @param cursor	游标结果集
	 * @param mClass	查询实体类
	 * @return
	 */
	public static <T> List<T> parseCursor(Cursor cursor, Class<T> mClass) {
		List<T> list = new ArrayList<T>();
		if(!isCursorRight(cursor)) {
			return list;
		}
		long count = cursor.getCount();
		EntityTable entityTable = EntityTableManager.getEntityTable(mClass);
		LinkedHashMap<String, Property> propertys = entityTable.getColumnMap();
		Property primaryKey = entityTable.getPrimaryKey();
		
		try { 
			for(int i = 0; i < count; i++) {
				cursor.moveToPosition(i);
				T entity = (T) mClass.newInstance();
				primaryKey.setValue(entity, cursor);
				for(String key : propertys.keySet()) {
					Property property = propertys.get(key);
					if (property!=null){
						property.setValue(entity, cursor);
					}
				}
				list.add(entity);
			}
		} catch (Exception e) {
			DBLog.debug("解析查询结果集出错", e);
			throw new IllegalArgumentException(e);
		} finally {
			closeCursor(cursor);
		}
		return list;
	}
	
	/**
	 * 解析查询游标结果集第一条记录(解析完成之后会关闭游标)
	 * Author: hyl
	 * Time: 2015-8-21上午10:30:14
	 * @param cursor	游标结果集
	 * @param mClass	查询实体类
	 * @return
	 */
	public static <T> T parseCursorOneResult(Cursor cursor, Class<T> mClass) {
		if(!isCursorRight(cursor)) {
			return null;
		}
		EntityTable entityTable = EntityTableManager.getEntityTable(mClass);
		LinkedHashMap<String, Property> propertys = entityTable.getColumnMap();
		Property primaryKey = entityTable.getPrimaryKey();
		T entity = null;
		try { 
			entity = (T) mClass.newInstance();
			cursor.moveToFirst();
			primaryKey.setValue(entity, cursor);
			for(String key : propertys.keySet()) {
				propertys.get(key).setValue(entity, cursor);
			}
		} catch (Exception e) {
			entity = null;
			DBLog.debug("解析查询结果集出错", e);
			throw new IllegalArgumentException(e);
		} finally {
			closeCursor(cursor);
		}
		return entity;
	}

	/**
	 * 查询实体类总数解析
	 * @param cursor
	 * @return
	 */
	public static long parseCursorTotal(Cursor cursor) {
		String value = parseCursorFirstCol(cursor);
		if(value == null) {
			value = "0";
		}
		return Long.parseLong(value);
	}
	
	/**
	 * 解析游标结果的第一条记录的第一列的字段值
	 * @param cursor
	 * @return
	 */
	public static String parseCursorFirstCol(Cursor cursor) {
		String value = null;
		if(!isCursorRight(cursor)) {
			return value;
		}
		try {
			cursor.moveToFirst();
			value = cursor.getString(0);
		} catch (Exception e) {
			value = null;
			DBLog.debug("解析实体类第一列结果出错", e);
		} finally {
			closeCursor(cursor);
		}
		return value;
	}
}
