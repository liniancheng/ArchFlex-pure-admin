package com.adtec.moia.base.engine.database.dialect;

import java.util.List;

import com.adtec.moia.base.engine.database.DatabaseException;
import com.adtec.moia.base.engine.database.bo.Column;
import com.adtec.moia.base.engine.database.bo.Forkey;
import com.adtec.moia.base.engine.database.bo.Index;
import com.adtec.moia.base.engine.database.bo.Table;

public abstract class Dialect {
	
	/**
	 * 获取表索引的sql
	 */
	public String getTableIndexSQL() throws DatabaseException {
		throw new DatabaseException(getClass().getName() + " does not support getTableIndexSQL");
	}
	/**
	 * 获取创建表的sql
	 */
	public String getCreateTableSQL(Table table, List<Column> columns) throws DatabaseException {
		throw new DatabaseException(getClass().getName() + " does not support getCreateTableSQL");
	}
	/**
	 * 获取创建索引的sql
	 */
	public String getCreateIndexSQL(Table table, List<Index> indexs) throws DatabaseException {
		throw new DatabaseException(getClass().getName() + " does not support getCreateIndexSQL");
	}
	/**
	 * 获取创建外键的sql
	 */
	public String getCreateForkeySQL(Table table, List<Forkey> forkeys) throws DatabaseException {
		throw new DatabaseException(getClass().getName() + " does not support getCreateForkeySQL");
	}
	/**
	 * 获得创建表字段sql
	 */
	public String getCreateColumnSQL(Table table, Column column) throws DatabaseException {
		throw new DatabaseException(getClass().getName() + " does not support getCreateColumnSQL");
	}
	/**
	 * 获取表空间的sql
	 */
	public String getTableSpaceSQL() throws DatabaseException {
		throw new DatabaseException(getClass().getName() + " does not support getTableSpaceSQL");
	}
	/**
	 * 判断是不是唯一索引
	 */
	public boolean isUniqueIndex(String str) throws DatabaseException {
		throw new DatabaseException(getClass().getName() + " does not support isUniqueIndex");
	}
	/**
	 * 获取第一条记录的sql
	 */
	public String getTopOneSql(String sql) throws DatabaseException {
		throw new DatabaseException(getClass().getName() + " does not support getTopOneSql");
	}
	/**
	 * 获取分页查询sql
	 */
	public String getPageSql(String sql, int pageSize, int startRow) throws DatabaseException {
		throw new DatabaseException(getClass().getName() + " does not support getPageSql");
	}
	/**
	 * 获取分页查询sql带一个字段的排序方式
	 */
	public String getPageSqlWithOrderMode(String sql, int pageSize, int startRow,String orderField,String orderMode) throws DatabaseException {
		throw new DatabaseException(getClass().getName() + " does not support getPageSql");
	}
}
