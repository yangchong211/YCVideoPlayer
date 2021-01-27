package com.yc.database.sql;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.sql.SQLException;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import com.yc.database.bean.BindSQL;
import com.yc.database.bean.EntityTable;
import com.yc.database.bean.PrimaryKey;
import com.yc.database.manager.EntityTableManager;
import com.yc.database.manager.SQLExecuteManager;
import com.yc.database.utils.CursorUtil;
import com.yc.database.utils.ValueUtil;


/**
 * <pre>
 *     @author yangchong
 *     email  : yangchong211@163.com
 *     time  : 2017/8/6
 *     desc  : 数据库操作类
 *     revise: 注：所有操作的实体必须实现无参构造函数
 * </pre>
 */
public class SQLiteDB {

	/**
	 * 数据库配置
	 */
	private SQLiteDBConfig mConfig;
	/**
	 * 数据库操作类
	 */
	private SQLiteDatabase mDB;
	/**
	 * SQL语句执行管理器
	 */
	private SQLExecuteManager mSQLExecuteManager;
	
	public SQLExecuteManager getSQLExecuteManager() {
		return mSQLExecuteManager;
	}

	public SQLiteDBConfig getConfig() {
		return mConfig;
	}
	
	public SQLiteDB(SQLiteDBConfig mConfig) {
		super();
		this.mConfig = mConfig;
		createDB();
	}
	
	/**
	 * 创建数据库
	 */
	private void createDB() {
		SQLiteHelper sqLiteHelper = new SQLiteHelper(mConfig);
		mDB = sqLiteHelper.getWritableDatabase();
		if(mDB == null) {
			throw new NullPointerException("创建数据库对象失败");
		}
		mSQLExecuteManager = new SQLExecuteManager(mDB);
	}
	
	/**
	 * 关闭当前数据库
	 */
	public void close() {
		this.mDB.close();
	}
	
	/**
	 * 判断当前数据库是否打开
	 * @return
	 */
	public boolean isOpen() {
		return this.mDB.isOpen();
	}
	
	/**
	 * 重新打开数据库
	 */
	public void reOpen() {
		if(!isOpen()) {
			createDB();
		}
	}

	/**
	 * 保存一个实体
	 * 当主键设置为自增长时，手动设置的主键值不会起作用，同时会自动加保存之后最新的主键值填充到对象中，并返回
	 * @param entity							对象
	 * @return
	 */
	public <T> long save(T entity) {
		if (entity==null){
			return -1;
		}
		EntityTableManager.checkOrCreateTable(mSQLExecuteManager, entity);
		BindSQL insertSQL = SQLBuilder.getInsertSQL(entity);
		long rowid = mSQLExecuteManager.insert(insertSQL);
		EntityTable entityTable = EntityTableManager.getEntityTable(entity);
		PrimaryKey key = entityTable.getPrimaryKey();
		if(key.isAutoGenerate()) {
			key.getField().setAccessible(true);
			try {
				key.setValue(entity, rowid);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return rowid;
	}
	
	/**
	 * 保存集合实体
	 * @param collection						集合
	 * @return									如果集合为空或者批量保存失败则返回-1，保存成功返回集合大小
	 */
	public <T> long save(Collection<T> collection) {
		long rowId = -1;
		if(ValueUtil.isEmpty(collection)) {
			return rowId;
		}
		try {
			mSQLExecuteManager.beginTransaction();
			Iterator<T> iterator = collection.iterator();
			while(iterator.hasNext()) {
				rowId = save(iterator.next());
				if(rowId == -1) {
					throw new SQLException("删除实体失败");
				}
			}
			mSQLExecuteManager.successTransaction();
			rowId = collection.size();
		} catch (SQLException e) {
			e.printStackTrace();
			rowId = -1;
		} finally {
			mSQLExecuteManager.endTransaction();
		}
		return rowId;
	}
	
	/**
	 * 删除指定实体(根据主键删除)
	 * @param entity	要删除的实体
	 */
	public <T> void delete(T entity) {
		if(ValueUtil.isEmpty(entity)) {
			return ;
		}
		EntityTableManager.checkOrCreateTable(mSQLExecuteManager, entity);
		mSQLExecuteManager.delete(SQLBuilder.getDeleteSQL(entity));
	}
	
	/**
	 * 删除集合中的实体(有事务控制)，每个实体根据主键删除
	 * @param collection	要删除的实体集合
	 */
	public <T> void delete(Collection<T> collection) {
		if(ValueUtil.isEmpty(collection)) {
			return;
		}
		
		try {
			Iterator<T> iterator = collection.iterator();
			this.mSQLExecuteManager.beginTransaction();
			while(iterator.hasNext()) {
				delete(iterator.next());
			}
			this.mSQLExecuteManager.successTransaction();
		} finally {
			this.mSQLExecuteManager.endTransaction();
		}
	}
	
	/**
	 * 删除实体类中指定主键的实体
	 * @param mClass				要删除的实体类
	 * @param primaryKeyValue		要删除的实体的主键值
	 */
	public void delete(Class<?> mClass, String primaryKeyValue) {
		if(ValueUtil.isEmpty(primaryKeyValue)) {
			throw new IllegalArgumentException("要删除的实体的主键不能为空");
		}
		EntityTableManager.checkOrCreateTable(mSQLExecuteManager, mClass);
		mSQLExecuteManager.delete(SQLBuilder.getDeleteSQL(mClass, primaryKeyValue));
	}
	
	/**
	 * 根据指定条件删除指定的实体
	 * @param mClass		要删除的实体类
	 * @param whereClause	where后面的条件句(delete from XXX where XXX)，参数使用占位符
	 * @param whereArgs		占位符参数
	 */
	public void delete(Class<?> mClass, String whereClause, String[] whereArgs) {
		EntityTableManager.checkOrCreateTable(mSQLExecuteManager, mClass);
		EntityTable entityTable = EntityTableManager.getEntityTable(mClass);
		delete(entityTable.getTableName(), whereClause, whereArgs);
	}
	
	/**
	 * 根据where条件句删除相关实体
	 * @param tableName			要删除的数据表
	 * @param whereClause		where后面的条件句(delete from XXX where XXX)，参数使用占位符
	 * @param whereArgs			占位符参数
	 * @return
	 */
	public void delete(String tableName, String whereClause, String[] whereArgs) {
		mSQLExecuteManager.delete(tableName, whereClause, whereArgs);
	}
	
	/**
	 * 删除,表名不能使用占位符
	 * @param sql			删除语句(参数使用占位符)
	 * @param bindArgs		占位符参数
	 */
	public void delete(String sql, String[] bindArgs) {
		mSQLExecuteManager.updateOrDelete(sql, bindArgs);
	}
	
	/**
	 * 删除实体类所有数据
	 * @param mClass
	 */
	public void deleteAll(Class<?> mClass) {
		EntityTableManager.checkOrCreateTable(mSQLExecuteManager, mClass);
		mSQLExecuteManager.delete(SQLBuilder.getDeleteSQL(mClass, null));
	}
	
	/**
	 * 更新指定实体(必须设置主键，根据主键更新)
	 * @param entity
	 * @return
	 */
	public <T> void update(T entity) {
		EntityTableManager.checkOrCreateTable(mSQLExecuteManager, entity);
		mSQLExecuteManager.update(SQLBuilder.getUpdateSQL(entity));
	}
	
	/**
	 * 更新指定集合数据(每个实体必须设置主键，根据主键更新)
	 * @param collection
	 */
	public <T> void update(Collection<T> collection) {
		if(ValueUtil.isEmpty(collection)) {
			return;
		}
		try {
			Iterator<T> iterator = collection.iterator();
			this.mSQLExecuteManager.beginTransaction();
			while(iterator.hasNext()) {
				update(iterator.next());
			}
			this.mSQLExecuteManager.successTransaction();
		} finally {
			this.mSQLExecuteManager.endTransaction();
		}
	}
	
	/**
	 * 更新
	 * @param sql
	 * @param bindArgs
	 */
	public void update(String sql, String[] bindArgs) {
		mSQLExecuteManager.updateOrDelete(sql, bindArgs);
	}
	
	/**
	 * 查询实体类全部数据
	 * @param mClass	要查询的实体类
	 * @return			实体列表
	 */
	public <T> List<T> queryAll(Class<T> mClass) {
		EntityTableManager.checkOrCreateTable(mSQLExecuteManager, mClass);
		Cursor cursor = mSQLExecuteManager.query(SQLBuilder.getQuerySQL(mClass));
		return CursorUtil.parseCursor(cursor, mClass);
	}
	
	/**
	 * 根据主键查询指定实体
	 * @param primaryKeyValue
	 * @return
	 */
	public <T> T query(Class<T> mClass, String primaryKeyValue) {
		EntityTableManager.checkOrCreateTable(mSQLExecuteManager, mClass);
		Cursor cursor = mSQLExecuteManager.query(SQLBuilder.getQuerySQLById(mClass, primaryKeyValue));
		return CursorUtil.parseCursorOneResult(cursor, mClass);
	}
	
	/**
	 * 根据条件查询实体类
	 * @param mClass		查询的实体类
	 * @param whereClause	查询条件where子句
	 * @param whereArgs		where子句参数
	 * @return
	 */
	public <T> List<T> query(Class<T> mClass, String whereClause, String[] whereArgs) {
		EntityTableManager.checkOrCreateTable(mSQLExecuteManager, mClass);
		Cursor cursor = mSQLExecuteManager.query(SQLBuilder.getQuerySQL(mClass, whereClause, whereArgs));
		return CursorUtil.parseCursor(cursor, mClass);
	}
	
	/**
	 * 根据条件查询符合条件的第一条实体类
	 * @param mClass		查询的实体类
	 * @param whereClause	查询条件where子句
	 * @param whereArgs		where子句参数
	 * @return	存在返回第一条实体类，不存在返回null
	 */
	public <T> T queryOne(Class<T> mClass, String whereClause, String[] whereArgs) {
		EntityTableManager.checkOrCreateTable(mSQLExecuteManager, mClass);
		Cursor cursor = mSQLExecuteManager.query(SQLBuilder.getQuerySQL(mClass, whereClause, whereArgs));
		List<T> list = CursorUtil.parseCursor(cursor, mClass);
		return list.size() > 0 ? list.get(0) : null;
	}
	
	/**
	 * 根据SQL语句查询实体类
	 * @param mClass		查询的实体类
	 * @param sql			查询条件where子句
	 * @param whereArgs		where子句参数
	 * @return
	 */
	public <T> List<T> queryBySQL(Class<T> mClass, String sql, String[] whereArgs) {
		EntityTableManager.checkOrCreateTable(mSQLExecuteManager, mClass);
		Cursor cursor = mSQLExecuteManager.query(new BindSQL(sql, whereArgs));
		return CursorUtil.parseCursor(cursor, mClass);
	}
	
	/**
	 * 分页查询
	 * @param mClass	查询实体类
	 * @param curPage	当前页码
	 * @param pageSize	每页数据条数
	 * @return
	 */
	public <T> List<T> queryPage(Class<T> mClass, int curPage, int pageSize) {
		EntityTableManager.checkOrCreateTable(mSQLExecuteManager, mClass);
		Cursor cursor = mSQLExecuteManager.query(SQLBuilder.getQueryPageSQL(mClass, curPage, pageSize));
		return CursorUtil.parseCursor(cursor, mClass);
	}
	
	/**
	 * 分页查询
	 * @param mClass	查询实体类，返回实体类型
	 * @param curPage	当前页码
	 * @param pageSize	每页数据条数
	 * @return
	 */
	/**
	 * 分页查询
	 * @param mClass				查询实体类，返回实体类型
	 * @param whereClause			查询语句
	 * @param whereArgs				查询语句中的参数
	 * @param curPage				当前页码
	 * @param pageSize				每页数据条数
	 * @return
	 */
	public <T> List<T> queryPage(Class<T> mClass, String whereClause, String[] whereArgs, int curPage, int pageSize) {
		EntityTableManager.checkOrCreateTable(mSQLExecuteManager, mClass);
		Cursor cursor = mSQLExecuteManager.query(SQLBuilder.getQueryPageSQL(mClass, whereClause, whereArgs, curPage, pageSize));
		return CursorUtil.parseCursor(cursor, mClass);
	}
	
	/**
	 * 查询实体类的总数据条数
	 * @param mClass	查询实体类
	 * @return
	 */
	public long queryTotal(Class<?> mClass) {
		EntityTableManager.checkOrCreateTable(mSQLExecuteManager, mClass);
		return queryTotal(mClass, null, null);
	}
	
	/**
	 * 查询实体类指定条件下的数据总数
	 * @param mClass			查询实体类
	 * @param whereClause		查询条件
	 * @param whereArgs			查询条件中的占位符参数
	 * @return
	 */
	public long queryTotal(Class<?> mClass, String whereClause, String[] whereArgs) {
		EntityTableManager.checkOrCreateTable(mSQLExecuteManager, mClass);
		Cursor cursor = mSQLExecuteManager.query(SQLBuilder.getTotalSQL(mClass, whereClause, whereArgs));
		return CursorUtil.parseCursorTotal(cursor);
	}
	
	/**
	 * 根据SQL语句查询数据条数(解析结果为第一列值)
	 * @param sql			查询SQL
	 * @param bindArgs		占位符参数值
	 * @return
	 */
	public long queryTotal(String sql, String[] bindArgs) {
		Cursor cursor = mSQLExecuteManager.query(sql, bindArgs);
		return CursorUtil.parseCursorTotal(cursor);
	}
	
	/**
	 * 查询指定实体类中是否存在指定主键值的实体对象
	 * @param mClass			查询实体类
	 * @param primaryKeyValue	实体主键值
	 * @return	存在返回true，不存在返回false
	 */
	public boolean queryIfExist(Class<?> mClass, String primaryKeyValue) {
		EntityTableManager.checkOrCreateTable(mSQLExecuteManager, mClass);
		EntityTable entityTable = EntityTableManager.getEntityTable(mClass);
		String whereClause = entityTable.getPrimaryKey().getColumn() + "=?";
		String[] whereArgs = {primaryKeyValue};
		long count = queryTotal(mClass, whereClause, whereArgs);
		return count > 0 ? true : false;
	}
	
	/**
	 * 根据查询条件判断是否存在指定的数据
	 * @param mClass		查询实体类
	 * @param whereClause	查询条件
	 * @param whereArgs		查询参数
	 * @return				存在返回true，不存在返回false
	 */
	public boolean queryIfExist(Class<?> mClass, String whereClause, String[] whereArgs) {
		long count = queryTotal(mClass, whereClause, whereArgs);
		return count > 0 ? true : false;
	}
	
	/**
	 * 根据SQL语句查询
	 * @param sql
	 * @param bindArgs
	 * @return
	 */
	public Cursor query(String sql, String[] bindArgs) {
		return mSQLExecuteManager.query(sql, bindArgs);
	}
	

	/**
	 * 根据查询条件查询指定实体的指定字段信息
	 * @param mClass		要查询的实体
	 * @param selectCols	要查询的数据库字段，多个查询字段间使用逗号隔开
	 * @param whereClause	查询条件(无查询条件时，可以传值null)
	 * @param whereArgs		查询条件参数值
	 * @return
	 */
	public <T> Cursor query(Class<T> mClass, String selectCols, String whereClause, String[] whereArgs) {
		EntityTableManager.checkOrCreateTable(mSQLExecuteManager, mClass);
		String tableName = EntityTableManager.getEntityTable(mClass).getTableName();
		String sql = "SELECT " + selectCols + " FROM " + tableName;
		if(!ValueUtil.isEmpty(whereClause)) {
			sql += " WHERE " + whereClause;
		}
		return query(sql, whereArgs);
	}
}
