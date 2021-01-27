package com.yc.database.sql;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;

import com.yc.database.bean.BindSQL;
import com.yc.database.bean.EntityTable;
import com.yc.database.bean.Property;
import com.yc.database.manager.EntityTableManager;
import com.yc.database.manager.FieldTypeManager;
import com.yc.database.utils.ValueUtil;


/**
 * <pre>
 *     @author yangchong
 *     email  : yangchong211@163.com
 *     time  : 2017/8/6
 *     desc  : SQL语句构造器
 *     revise:
 * </pre>
 */
public class SQLBuilder {
	
	/**
	 * 获取当前实体类的表名
	 * @param mClass
	 * @return
	 */
	public static String getTableName(Class<?> mClass) {
		com.yc.database.annotation.Table anno = mClass.getAnnotation(com.yc.database.annotation.Table.class);
		if(anno == null || ValueUtil.isEmpty(anno.name())) {
			return mClass.getSimpleName();
		}
		return anno.name();
	}
	
	/**
	 * 根据实体对象构造建表语句
	 * @param entity
	 * @return
	 */
	public static String getCreateTableSQL(EntityTable entity) {
		StringBuilder createTableSql = new StringBuilder();
		createTableSql.append("CREATE TABLE IF NOT EXISTS ");
		createTableSql.append(entity.getTableName());//表名
		createTableSql.append("(");
		createTableSql.append(entity.getPrimaryKey().getColumn());//主键字段
		createTableSql.append(" ");
		createTableSql.append(FieldTypeManager.getColumnTypeValue(entity.getPrimaryKey().getField()));//主键字段类型
		createTableSql.append(" PRIMARY KEY ");//主键
		if(entity.getPrimaryKey().isAutoGenerate()) {
			createTableSql.append(" AUTOINCREMENT ");
		}
		LinkedHashMap<String, Property> columnMap = entity.getColumnMap();
		for(String key : columnMap.keySet()) {
			createTableSql.append(",");
			createTableSql.append(columnMap.get(key).getColumn()).append(" ");//字段名
			createTableSql.append(FieldTypeManager.getColumnTypeValue(columnMap.get(key).getField()));//字段类型
			if(!ValueUtil.isEmpty(columnMap.get(key).getDefaultValue())) {
				createTableSql.append(" ").append("DEFAULT ").append(columnMap.get(key).getDefaultValue());
			}
		}
		createTableSql.append(")");
		return createTableSql.toString();
	}
	
	/**
	 * 检查表是否存在语句
	 * @param tableName
	 * @return
	 */
	public static String getCheckTableExistSQL(String tableName) {
		return "SELECT COUNT(*) TOTALCOUNT FROM SQLITE_MASTER WHERE UPPER(TYPE) ='TABLE' AND NAME = '" + tableName + "'";
	}
	
	/**
	 * 查询某个表中所有的字段
	 * @param tableName
	 * @return
	 */
	public static String getTableAllColumnSQL(String tableName) {
		return "SELECT * FROM "  + tableName + " LIMIT 0";
	}
	
	/**
	 * 更新表结构SQL(SQLite不支持删除列)
	 * @param tableName
	 * @param property
	 * @return
	 */
	public static String getAlterTableSQL(String tableName, Property property) {
		return "ALTER TABLE " + tableName + 
			   " ADD COLUMN " + property.getColumn() + " " + FieldTypeManager.getColumnTypeValue(property.getField()) +
			   (ValueUtil.isEmpty(property.getDefaultValue()) ? "" : " DEFAULT " + property.getDefaultValue());
	}
	
	/**
	 * 构造插入语句
	 * @param entity	要插入的实体对象
	 * @return
	 */
	public static <T> BindSQL getInsertSQL(T entity) {
		EntityTable entityTable = EntityTableManager.getEntityTable(entity);
		
		boolean isAutoIncrement = entityTable.getPrimaryKey().isAutoGenerate();
		
		String tableName = entityTable.getTableName();
		Collection<Property> propertys = entityTable.getColumnMap().values();
		Object[] bindArgs = null;
		if(isAutoIncrement) {//如果为自增长主键，则在构造插入语句的时候不需要构造主键列，数据库会自动为自增长主键设置值
			bindArgs = new Object[propertys.size()];
		} else {
			bindArgs = new Object[propertys.size() + 1];
		}

		StringBuilder sqlBuilder = new StringBuilder();
		StringBuilder argsBuidler = new StringBuilder();
		int i = 0;
		
		sqlBuilder.append("INSERT INTO ").append(tableName).append("(");
		argsBuidler.append(" VALUES(");
		
		if(!isAutoIncrement) {//主键不是自增长列，需要自己设置主键值
			sqlBuilder.append(entityTable.getPrimaryKey().getColumn()).append(",");//主键
			argsBuidler.append("?").append(",");
			if(ValueUtil.isEmpty(entityTable.getPrimaryKey().getValue(entity))) {//判断主键值是否为空
				throw new IllegalArgumentException("非自增长主键必须手动设置主键值");
			}
			bindArgs[i++] = entityTable.getPrimaryKey().getValue(entity);
		}
		
		Iterator<Property> iterator = propertys.iterator();
		Property property;
		while(iterator.hasNext()) {
			property = iterator.next();
			
			sqlBuilder.append(property.getColumn());
			argsBuidler.append("?");
			
			bindArgs[i++] = getPropertyValue(property, entity);
			
			sqlBuilder.append(",");
			argsBuidler.append(",");
		}
		sqlBuilder.deleteCharAt(sqlBuilder.length() - 1);
		argsBuidler.deleteCharAt(argsBuidler.length() - 1);
		sqlBuilder.append(")");
		argsBuidler.append(")");
		sqlBuilder.append(argsBuidler);
		
		BindSQL bindSQL = new BindSQL(sqlBuilder.toString());
		bindSQL.setBindArgs(bindArgs);
		return bindSQL;
	}
	
	private static <T> String getPropertyValue(Property property, T entity) {
		String value = null;
		Object obj = property.getValue(entity);//此值的类型为多种基本类型，如果为int、long  直接赋值给value会运行报错
		switch(FieldTypeManager.getFieldType(property.getField())) {
			case FieldTypeManager.BASE_TYPE_DOUBLE:
			case FieldTypeManager.BASE_TYPE_FLOAT:
			case FieldTypeManager.BASE_TYPE_INT:
			case FieldTypeManager.BASE_TYPE_LONG:
			case FieldTypeManager.BASE_TYPE_SHORT:
				if(Double.parseDouble(obj.toString()) == 0 && !ValueUtil.isEmpty(property.getDefaultValue())) {
					value = property.getDefaultValue();
				} else {
					value = obj.toString();
				}
				break;
			case FieldTypeManager.BASE_TYPE_BOOLEAN:
				if(Boolean.parseBoolean(obj.toString()) == false && !ValueUtil.isEmpty(property.getDefaultValue())) {
					value = property.getDefaultValue();
				} else {
					value = obj.toString();
				}
				break;
			case FieldTypeManager.BASE_TYPE_CHAR:
			case FieldTypeManager.BASE_TYPE_STRING:
			case FieldTypeManager.BASE_TYPE_DATE:
				if(!ValueUtil.isEmpty(obj)) {
					value = obj.toString();
				} else {
					if(!ValueUtil.isEmpty(property.getDefaultValue())) {
						value = property.getDefaultValue();
					}
				}
				break;
		}
		return value;
	}
	
	/**
	 * 构造删除语句
	 * @param entity	要删除的实体
	 * @return
	 */
	public static <T> BindSQL getDeleteSQL(T entity) {
		EntityTable entityTable = EntityTableManager.getEntityTable(entity);
		StringBuilder sqlBuilder = new StringBuilder();
		StringBuilder argsBuilder = new StringBuilder();
		
		sqlBuilder.append("DELETE FROM ").append(entityTable.getTableName());
		sqlBuilder.append(" WHERE ").append(entityTable.getPrimaryKey().getColumn()).append(" = ?");
		if(ValueUtil.isEmpty(entityTable.getPrimaryKey().getValue(entity))) {
			throw new IllegalArgumentException("未设置要删除实体的主键");
		}
		argsBuilder.append(entityTable.getPrimaryKey().getValue(entity));
		
		return new BindSQL(sqlBuilder.toString(), new String[]{argsBuilder.toString()});
	}
	
	/**
	 * 构造要删除的语句
	 * @param mClass				要删除的实体类
	 * @param primaryKeyValue		要删除的实体的主键，主键为空，则删除所有的数据
	 * @return
	 */
	public static BindSQL getDeleteSQL(Class<?> mClass, String primaryKeyValue) {
		EntityTable entityTable = EntityTableManager.getEntityTable(mClass);
		StringBuilder sqlBuilder = new StringBuilder();
		
		sqlBuilder.append("DELETE FROM ").append(entityTable.getTableName());
		String[] bindArgs = null;
		if(!ValueUtil.isEmpty(primaryKeyValue)) {
			sqlBuilder.append(" WHERE ").append(entityTable.getPrimaryKey().getColumn()).append(" = ?");
			bindArgs = new String[]{primaryKeyValue};
		}
		
		return new BindSQL(sqlBuilder.toString(), bindArgs);
	}
	
	/**
	 * 构造更新数据
	 * @param entity
	 * @return
	 */
	public static <T> BindSQL getUpdateSQL(T entity) {
		EntityTable entityTable = EntityTableManager.getEntityTable(entity);
		
		if(ValueUtil.isEmpty(entityTable.getPrimaryKey().getValue(entity))) {
			throw new IllegalArgumentException("未设置要删除实体的主键");
		}
		
		StringBuilder sqlBuilder = new StringBuilder();
		
		sqlBuilder.append("UPDATE ");
		sqlBuilder.append(entityTable.getTableName());
		sqlBuilder.append(" SET ");
		
		Collection<Property> propertys = entityTable.getColumnMap().values();
		Object[] bindArgs = new Object[propertys.size() + 1];
		int i = 0;
		
		Iterator<Property> iterator = propertys.iterator();
		Property property;
		while(iterator.hasNext()) {
			property = iterator.next();
			
			sqlBuilder.append(property.getColumn());
			sqlBuilder.append(" = ?,");
			bindArgs[i++] = property.getValue(entity);
		}
		sqlBuilder.deleteCharAt(sqlBuilder.length() - 1);
		sqlBuilder.append(" WHERE ");
		sqlBuilder.append(entityTable.getPrimaryKey().getColumn());
		sqlBuilder.append(" = ?");
		bindArgs[i++] = entityTable.getPrimaryKey().getValue(entity);
		
		return new BindSQL(sqlBuilder.toString(), bindArgs);
	}
	
	/**
	 * 查询指定实体的全部数据
	 * @param mClass	要查询的实体类
	 * @return
	 */
	public static String getQuerySQL(Class<?> mClass) {
		String tableName = EntityTableManager.getEntityTable(mClass).getTableName();
		return "SELECT * FROM " + tableName;
	}
	
	/**
	 * 查询指定实体的全部数据
	 * @param mClass	要查询的实体类
	 * @return
	 */
	public static BindSQL getQuerySQLById(Class<?> mClass, String primaryValue) {
		EntityTable entityTable = EntityTableManager.getEntityTable(mClass);
		StringBuilder sqlBuilder = new StringBuilder();
		sqlBuilder.append("SELECT * FROM ");
		sqlBuilder.append(entityTable.getTableName());
		sqlBuilder.append(" WHERE ");
		sqlBuilder.append(entityTable.getPrimaryKey().getColumn());
		sqlBuilder.append(" = ?");
		return new BindSQL(sqlBuilder.toString(), new String[]{primaryValue});
	}
	
	/**
	 * 查询指定实体的全部数据
	 * @param mClass		要查询的实体类
	 * @param whereClause	where子句
	 * @param whereArgs		where字句绑定参数
	 * @return
	 */
	public static BindSQL getQuerySQL(Class<?> mClass, String whereClause, String[] whereArgs) {
		EntityTable entityTable = EntityTableManager.getEntityTable(mClass);
		StringBuilder sqlBuilder = new StringBuilder();
		sqlBuilder.append("SELECT * FROM ");
		sqlBuilder.append(entityTable.getTableName());
		sqlBuilder.append(" WHERE ");
		sqlBuilder.append(whereClause);
		return new BindSQL(sqlBuilder.toString(), whereArgs);
	}
	
	/**
	 * 查询实体类数据总条数
	 * @param mClass
	 * @return
	 */
	public static BindSQL getTotalSQL(Class<?> mClass, String whereClause, String[] whereArgs) {
		String tableName = EntityTableManager.getEntityTable(mClass).getTableName();
		String sql = "SELECT COUNT(*) TOTALCOUNT FROM " + tableName;
		if(!ValueUtil.isEmpty(whereClause)) {
			sql = sql + " WHERE " + whereClause;
		}
		return new BindSQL(sql, whereArgs);
	}
	
	/**
	 * 获取分页查询语句
	 * @param mClass		要查询的实体类
	 * @param curPage		查询的当前页码
	 * @param pageSize		每页数据数
	 * @return
	 */
	public static BindSQL getQueryPageSQL(Class<?> mClass, int curPage, int pageSize) {
		EntityTable entityTable = EntityTableManager.getEntityTable(mClass);
		StringBuilder sqlBuilder = new StringBuilder();
		sqlBuilder.append("SELECT * FROM ");
		sqlBuilder.append(entityTable.getTableName());
		sqlBuilder.append(" LIMIT ? OFFSET ? * ? ");
		String cur = String.valueOf(curPage - 1);
		String size = String.valueOf(pageSize);
		return new BindSQL(sqlBuilder.toString(), new String[]{size, cur, size});
	}
	
	/**
	 * 获取分页查询语句
	 * @param mClass		要查询的实体类
	 * @param whereClause	查询where子句
	 * @param whereArgs		where子句参数
	 * @param curPage		查询的当前页码
	 * @param pageSize		每页数据数
	 * @return
	 */
	public static BindSQL getQueryPageSQL(Class<?> mClass, String whereClause, String[] whereArgs, int curPage, int pageSize) {
		EntityTable entityTable = EntityTableManager.getEntityTable(mClass);
		StringBuilder sqlBuilder = new StringBuilder();
		sqlBuilder.append("SELECT * FROM ");
		sqlBuilder.append(entityTable.getTableName());
		if(!ValueUtil.isEmpty(whereClause)) {
			sqlBuilder.append(" WHERE ");
			sqlBuilder.append(whereClause);
		}
		sqlBuilder.append(" LIMIT ? OFFSET ? * ? ");
		String cur = String.valueOf(curPage - 1);
		String size = String.valueOf(pageSize);
		
		int length = whereArgs == null ? 0 : whereArgs.length;
		String[] newWhereArgs = new String[length + 3];
		if(length > 0) {
			System.arraycopy(whereArgs, 0, newWhereArgs, 0, whereArgs.length);
		}
		newWhereArgs[length] = size;
		newWhereArgs[length + 1] = cur;
		newWhereArgs[length + 2] = size;
		return new BindSQL(sqlBuilder.toString(), newWhereArgs);
	}
}
