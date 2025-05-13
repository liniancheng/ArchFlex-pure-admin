package com.adtec.rdc.base.resource.utils;
/**
 * @author: dengchf     
 * @date:   2020年3月4日 11:53:08   
 * @Description:    TODO
 * @version V1.0 
 * @Copyright: adtec
 */

import java.sql.Connection;
import java.sql.SQLException;

import com.adtec.rdc.base.common.enums.DBLinkEnum;
import com.adtec.rdc.base.common.exception.ServiceException;
import com.adtec.rdc.base.common.model.vo.SysDblinkVo;
import com.adtec.rdc.base.enums.ErrorCodeEnum;
import com.adtec.rdc.base.migrate.config.BaseDBConfig;
/**
 * @author: dengchf     
 * @date:   2020年3月5日 10:35:34   
 * @Description:    TODO 数据源工具类
 * @version V1.0 
 * @Copyright: adtec
 */
import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.util.StringUtils;

public class DatabaseUtils {
	/**
	 * 获取数据源配置信息
	 */
	private static BaseDBConfig getDbConfig(SysDblinkVo dblink) {
		BaseDBConfig config = new BaseDBConfig();
		config.setDriverClassName(dblink.getDblinkDriver());
		config.setUrl(dblink.getDblinkUrl());
		config.setUsername(dblink.getDblinkUser());
		config.setPassword(dblink.getDblinkPwd());

		return config;
	}

	public static Boolean testConnection(SysDblinkVo dblink) {
		if (DBLinkEnum.POOL_NONE.getType().equals(dblink.getPoolType())) {
			return testJDBCConnection(getDbConfig(dblink));
		}
		return testDruidConnection(dblink);

	}

	private static Boolean testDruidConnection(SysDblinkVo dblink) {
		DruidDataSource dds = new DruidDataSource();
		try {
			dds.setUrl(dblink.getDblinkUrl());
			dds.setDriverClassName(dblink.getDblinkDriver());
			dds.setUsername(dblink.getDblinkUser());
			dds.setPassword(dblink.getDblinkPwd());
			if (dblink.getInitialSize() != null) {
				dds.setInitialSize(dblink.getInitialSize());
			}
			if (dblink.getMaxActive() != null) {
				dds.setMaxWait(dblink.getMaxWait());
			}
			if (dblink.getMaxActive() != null) {
				dds.setMaxActive(dblink.getMaxActive());
			}
			if (dblink.getTimeBtnEvictionRunsMillis() != null) {
				dds.setTimeBetweenEvictionRunsMillis(dblink.getTimeBtnEvictionRunsMillis());
			}
			if (dblink.getMinEvictableIdleTimeMillis() != null) {
				dds.setMinEvictableIdleTimeMillis(dblink.getMinEvictableIdleTimeMillis());
			}
			if (!StringUtils.isEmpty(dblink.getTestOnBorrow())) {
				dds.setTestOnBorrow(DBLinkEnum.FLAG_TRUE.getType().equals(dblink.getTestOnBorrow()));
			}
			if (!StringUtils.isEmpty(dblink.getTestOnReturn())) {
				dds.setTestOnReturn(DBLinkEnum.FLAG_TRUE.getType().equals(dblink.getTestOnReturn()));
			}
			if (!StringUtils.isEmpty(dblink.getPoolPreparedStatements())) {
				dds.setPoolPreparedStatements(
						DBLinkEnum.FLAG_TRUE.getType().equals(dblink.getPoolPreparedStatements()));
			}
			if (dblink.getMaxPsCatchSize() != null) {
				dds.setMaxPoolPreparedStatementPerConnectionSize(dblink.getMaxPsCatchSize());
			}
			try {
				if (!StringUtils.isEmpty(dblink.getFilters())) {
					dds.setFilters(dblink.getFilters());
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			if(!StringUtils.isEmpty(dblink.getRemoveAbandoned())) {
				dds.setRemoveAbandoned(DBLinkEnum.FLAG_TRUE.getType().equals(dblink.getRemoveAbandoned()));
			}
			if (dblink.getRemoveAbandonedTimeout() != null) {
				dds.setRemoveAbandonedTimeout(dblink.getRemoveAbandonedTimeout());
			}
			

			dds.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new ServiceException(ErrorCodeEnum.DB_TEST.getErrorCode(),
					ErrorCodeEnum.DB_TEST.getMessage() + "：" + e.getMessage());
		} finally {
			if (!dds.isClosed()) {
				dds.close();
			}
		}
		return true;
	}

	private static Boolean testJDBCConnection(BaseDBConfig config) {
		Connection conn = null;
		try {
			conn = config.getConnection();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			throw new ServiceException(ErrorCodeEnum.DB_TEST.getErrorCode(),
					ErrorCodeEnum.DB_TEST.getMessage() + "：" + e.getMessage());
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return Boolean.TRUE;
	}
}
