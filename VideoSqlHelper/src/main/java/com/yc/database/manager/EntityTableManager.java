package com.yc.database.manager;

import android.database.Cursor;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import com.yc.database.sql.SQLBuilder;
import com.yc.database.annotation.Column;
import com.yc.database.annotation.NotDBColumn;
import com.yc.database.annotation.PrimaryKey;
import com.yc.database.bean.EntityTable;
import com.yc.database.bean.Property;
import com.yc.database.utils.CursorUtil;
import com.yc.database.utils.ValueUtil;


/**
 * <pre>
 *     @author yangchong
 *     email  : yangchong211@163.com
 *     time  : 2017/8/6
 *     desc  : 实体类管理对象
 *     revise:
 * </pre>
 */
public final class EntityTableManager implements Serializable {
	/**
	 * uid
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 每张表的相关信息缓存集合<实体类{@link}Class, {@link}Entity>
	 */
	private static HashMap<Class<?>, EntityTable> mTableMap = new HashMap<Class<?>, EntityTable>();
	
	/**
	 * 获取EntityTable对象
	 * @param entity
	 * @return
	 */
	public static <T> EntityTable getEntityTable(T entity) {
		return getEntityTable(entity.getClass());
	}
	
	/**
	 * 获取EntityTable对象
	 * @param mClass
	 * @return
	 */
	public static EntityTable getEntityTable(Class<?> mClass) {
		if(mTableMap.containsKey(mClass)) {
			return mTableMap.get(mClass);
		} else {
			return createEntityTable(mClass);
		}
	}

	/**
	 * 检查实体类表是否存在，不存在则创建该实体表，线程安全
	 * @param entity
	 */
	public synchronized static <T> void checkOrCreateTable(SQLExecuteManager sqlExecuteManager, T entity) {
		Class<?> aClass = entity.getClass();
		checkOrCreateTable(sqlExecuteManager, aClass);
	}
	
	/**
	 * 检查指定类所表示的表是否存在，不存在则创建该实体表，线程安全
	 * @param sqlExecuteManager
	 * @param mClass
	 */
	public synchronized static void checkOrCreateTable(SQLExecuteManager sqlExecuteManager, Class<?> mClass) {
		if(!mTableMap.containsKey(mClass)) {//先在缓存中查找，缓存中不存在
			EntityTable entityTable = createEntityTable(mClass);
			mTableMap.put(mClass, entityTable);
			if(!checkTableIsInDB(sqlExecuteManager, entityTable.getTableName())) {//数据库中不存在
				createTable(sqlExecuteManager, entityTable);
			} else {//数据库中存在该表
				//对已存在表的字段与最新的实体对象进行比较，看是否需要更新表的字段信息
				checkOrAlterTableColumn(sqlExecuteManager, entityTable);
			}
		} else {//缓存中存在实体表映射关系，检查当前数据库中是否存在该表，不存在创建该表
			if(!checkTableIsInDB(sqlExecuteManager, mTableMap.get(mClass).getTableName())) {//数据库中不存在
				createTable(sqlExecuteManager, mTableMap.get(mClass));
			}
		}
	}
	
	/**
	 * 检查表的字段信息，实体中有新增字段，则给表添加相应的字段
	 * @param sqlExecuteManager
	 * @param entityTable
	 */
	public static void checkOrAlterTableColumn(SQLExecuteManager sqlExecuteManager, EntityTable entityTable) {
		Cursor cursor = sqlExecuteManager.query(SQLBuilder.getTableAllColumnSQL(entityTable.getTableName()));
		LinkedHashMap<String, Property> propertys = entityTable.getColumnMap();
		//数据表中已有的字段信息
		List<String> columns = Arrays.asList(cursor.getColumnNames());
		
		List<Property> addColumns = new ArrayList<Property>();
		for(String key : propertys.keySet()) {
			Property property = propertys.get(key);
			if (property!=null){
				if(!columns.contains(property.getColumn())) {
					//数据表中不包含当前实体属性字段信息，说明当前实体属性字段需要新增到数据表中
					addColumns.add(propertys.get(key));
				}
			}
		}
		
		if(addColumns.size() > 0) {
			try {
				sqlExecuteManager.beginTransaction();
				for(Property column : addColumns) {
					sqlExecuteManager.execSQL(SQLBuilder.getAlterTableSQL(entityTable.getTableName(), column));
				}
				sqlExecuteManager.successTransaction();
			} finally {
				sqlExecuteManager.endTransaction();
			}
		}
	}
	
	/**
	 * 创建实体类
	 * @param mClass
	 */
	public static EntityTable createEntityTable(Class<?> mClass) {
		EntityTable entity = new EntityTable(mClass);
		entity.setTableName(SQLBuilder.getTableName(mClass));

		Class<?> claxx = mClass;
		while(claxx != Object.class) {
			setEntityTableField(entity, claxx);
			claxx = claxx.getSuperclass();
		}
		
		if(entity.getPrimaryKey() == null) {
			throw new RuntimeException("必须为实体" + mClass.getName() + "设置主键---[在要设置主键的字段上添加注解PrimaryKey来设置主键]");
		}
		return entity;
	}

	private static void setEntityTableField(EntityTable entity, Class claxx) {
		/*
		 * 获取Class下声明的所有字段信息(包括：public,protected,private,默认级别， 四种访问级别的字段信息)
		 * 如果该Class没有声明任何字段或是Class代表一个基本类型、数组或void，则返回数组长度为0
		 * */
		Field[] fields = claxx.getDeclaredFields();
		String columnName = "";//字段列名
		for(int i = 0; i < fields.length; i++) {
			Field field = fields[i];

			if (Modifier.isStatic(field.getModifiers())) {//过滤掉static静态字段
				continue;
			}

			if(FieldTypeManager.getFieldType(field) == FieldTypeManager.NOT_BASE_TYPE) {//过滤掉非基本类型字段
				continue;
			}

			if(entity.getPrimaryKey() == null) {//主键设置过后即不再遍历主键注解
				PrimaryKey key = field.getAnnotation(PrimaryKey.class);
				if(key != null) {//声明了主键字段
					if(key.isAutoGenerate() && !FieldTypeManager.isAutoIncrementType(field)) {
						throw new RuntimeException("自增长主键字段类型不正确，请设置自增长字段类型为long");
					}
					if(ValueUtil.isEmpty(key.columnName())) {//没有通过注解设置列名，默认取字段名称为列名
						columnName = field.getName();
					} else {
						columnName = key.columnName();
					}
					if(Arrays.binarySearch(SQLExecuteManager.SQLITE_KEYWORDS, columnName.toUpperCase()) >= 0) {
						throw new IllegalArgumentException("字段名或注解columnName属性不能为SQLite关键字：" + columnName);
					}
					com.yc.database.bean.PrimaryKey primaryKey = new com.yc.database.bean.PrimaryKey();
					primaryKey.setField(field);
					primaryKey.setColumn(columnName);
					primaryKey.setAutoGenerate(key.isAutoGenerate());//获取是否自动增长
					entity.setPrimaryKey(primaryKey);
					continue;
				}
			}

			Property property = new Property();
			property.setField(field);

			Column column = field.getAnnotation(Column.class);
			if(column != null) {//声明了 字段  注解
				if(ValueUtil.isEmpty(column.columnName())) {//没有通过注解设置列名，默认取字段名称为列名
					columnName = field.getName();
				} else {
					columnName = column.columnName();
				}
				if(Arrays.binarySearch(SQLExecuteManager.SQLITE_KEYWORDS, columnName.toUpperCase()) >= 0) {
					throw new IllegalArgumentException("字段名或注解columnName属性不能为SQLite关键字：" + columnName);
				}
				if(!ValueUtil.isEmpty(column.defaultValue())) {//设置了字段默认值
					property.setDefaultValue(column.defaultValue());
				}
				property.setColumn(columnName);
				entity.getColumnMap().put(property.getColumn(), property);
				continue;
			}

			NotDBColumn notDbColumn = field.getAnnotation(NotDBColumn.class);
			if(notDbColumn != null) {//非数据库字段不作处理
				continue;
			}

			if(Arrays.binarySearch(SQLExecuteManager.SQLITE_KEYWORDS, field.getName().toUpperCase()) >= 0) {
				throw new IllegalArgumentException("注解字段名不能为SQLite关键字：" + columnName);
			}

			//没有设置注解的字段，以字段名为数据库列名
			property.setColumn(field.getName());
			entity.getColumnMap().put(property.getColumn(), property);
		}
	}
	
	/**
	 * 根据实体类创建数据库表
	 * @param sqlExecuteManager
	 * @param entity
	 */
	public static void createTable(SQLExecuteManager sqlExecuteManager, EntityTable entity) {
		dropTable(sqlExecuteManager, entity);//先删除表
		String createTableSQL = SQLBuilder.getCreateTableSQL(entity);//构造建表语句
		sqlExecuteManager.execSQL(createTableSQL);//建表
	}
	
	/**
	 * 删除实体类对应的数据库表
	 * @param sqlExecuteManager
	 * @param entity
	 */
	public static void dropTable(SQLExecuteManager sqlExecuteManager, EntityTable entity) {
		sqlExecuteManager.dropTable(entity.getTableName());
		mTableMap.remove(entity.getClass());
	}
	
	/**
	 * 清除实体表缓存对象
	 */
	public static void clear() {
		if (mTableMap!=null){
			mTableMap.clear();
		}
	}
	
	/**
	 * 检查数据库中是否存在指定表
	 * @param sqlExecuteManager
	 * @param tableName
	 * @return
	 */
	public static boolean checkTableIsInDB(SQLExecuteManager sqlExecuteManager, String tableName) {
		Cursor cursor = null;
		cursor = sqlExecuteManager.query(SQLBuilder.getCheckTableExistSQL(tableName));
		long total = CursorUtil.parseCursorTotal(cursor);
		return total > 0 ? true : false;
	}
}
