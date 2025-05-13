package com.adtec.moia.base.engine.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import com.adtec.moia.base.engine.database.bo.Database;
import com.adtec.moia.base.engine.database.dialect.DB2Dialect;
import com.adtec.moia.base.engine.database.dialect.Dialect;
import com.adtec.moia.base.engine.database.dialect.HSQLDialect;
import com.adtec.moia.base.engine.database.dialect.InformixDialect;
import com.adtec.moia.base.engine.database.dialect.MySQLDialect;
import com.adtec.moia.base.engine.database.dialect.OracleDialect;
import com.adtec.moia.base.engine.database.dialect.SQLServerDialect;
import com.adtec.moia.base.engine.database.dialect.SybaseDialect;
import com.adtec.rdc.base.common.exception.ServiceException;

public class DatabaseManage {
	/**
	 * 关闭连接
	 */
	public static void closeConnection(Connection conn) {
		if (conn != null) {
			try {
				conn.close();
			} catch (Exception e) {
				conn = null;
			}
		}
	}

	/**
	 * 关闭Statement
	 */
	public static void closeStatement(Statement stmt) {
		if (stmt != null) {
			try {
				stmt.close();
			} catch (Exception e) {
				stmt = null;
			}
		}
	}
	/**
	 * 关闭ResultSet
	 */
	public static void closeResultSet(ResultSet rs) {
		if (rs != null) {
			try {
				rs.close();
			} catch (Exception e) {
				rs = null;
			}
		}
	}

	/**
	 * 回滚
	 */
	public static void rollbackConnection(Connection conn) {
		if (conn != null) {
			try {
				conn.rollback();
			} catch (Exception e) {
			}
		}
	}
	
	/**
	 * 获取Dialect
	 */
	public static Dialect getDialect(int dbType) {
		if (dbType == DatabaseConstants.DB_TYPE_ORACLE) {
			return new OracleDialect();
		} else if (dbType == DatabaseConstants.DB_TYPE_SQLSERVER) {
			return new SQLServerDialect();
		} else if (dbType == DatabaseConstants.DB_TYPE_MYSQL) {
			return new MySQLDialect();
		} else if (dbType == DatabaseConstants.DB_TYPE_DB2) {
			return new DB2Dialect();
		} else if (dbType == DatabaseConstants.DB_TYPE_INFORMIX) {
			return new InformixDialect();
		} else if (dbType == DatabaseConstants.DB_TYPE_SYBASE) {
			return new SybaseDialect();
		} else if (dbType == DatabaseConstants.DB_TYPE_HSQL) {
			return new HSQLDialect();
		} 
		return null;
	}

	/**
	 * 获取Dialect
	 */
	public static Dialect getDialect(String dbType) {
		if (dbType.indexOf(DatabaseConstants.DIALECT_TYPE_ORACLE)>-1) {
			return new OracleDialect();
		} else if (dbType.indexOf(DatabaseConstants.DIALECT_TYPE_SQLSERVER)>-1) {
			return new SQLServerDialect();
		} else if (dbType.indexOf(DatabaseConstants.DIALECT_TYPE_MYSQL)>-1) {
			return new MySQLDialect();
		} else if (dbType.indexOf(DatabaseConstants.DIALECT_TYPE_DB2)>-1) {
			return new DB2Dialect();
		} else if (dbType.indexOf(DatabaseConstants.DIALECT_TYPE_INFORMIX)>-1) {
			return new InformixDialect();
		} else if (dbType.indexOf(DatabaseConstants.DIALECT_TYPE_SYBASE)>-1) {
			return new SybaseDialect();
		} else if (dbType.indexOf(DatabaseConstants.DIALECT_TYPE_HSQL)>-1) {
			return new HSQLDialect();
		} 
		return null;
	}
	
	/**
	 * 获取连接
	 */
	public static Connection getConnection(Database database) throws ServiceException {
		Connection conn = null;
		try {
			Class.forName(database.getJdbcDriver());
			String url = database.getJdbcUrl();
			String user = database.getDbUser();
			String pass = database.getDbPwd();
			DriverManager.setLoginTimeout(3000);
			Properties props = new Properties();
			props.put("user", user);
			props.put("password", pass);
			props.put("remarksReporting","true");
			conn = DriverManager.getConnection(url, props);		
		} catch (ClassNotFoundException e) {
			throw new ServiceException("数据库连接异常：ClassNotFoundException异常");
		} catch (SQLException e) {
			throw new ServiceException("SQLException异常");
		} catch (Exception e) {
			throw new ServiceException("数据库连接异常");
		}
		return conn;
	}
}
