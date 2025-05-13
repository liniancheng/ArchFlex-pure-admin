package com.adtec.rdc.base.migrate.utils;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.adtec.rdc.base.common.exception.ServiceException;
import com.adtec.rdc.base.common.model.bo.TreeNode;
import com.adtec.rdc.base.migrate.config.BaseDBConfig;
import com.adtec.rdc.base.migrate.model.bo.MigrateTable;
import com.adtec.rdc.base.migrate.model.bo.MigrateTableColumn;
import com.adtec.rdc.base.migrate.model.po.SysMigrateInfo;

/**
 * 数据迁移sql工具类
 * 
 * @author dengchf
 *
 */
public class MigrateUtils {

	private static final String MIG_KEY = "id";
	private static final String MIG_VAL = "text";

	private static final String COL_COLUMN_NAME = "COLUMN_NAME";
	private static final String COL_DATA_TYPE = "DATA_TYPE";
	private static final String COL_TYPE_NAME = "TYPE_NAME";
	private static final String JDBC_PATTERN = "%";

	private static final int default_submit_num = 100;

	/**
	 * 获取表字段信息
	 * 
	 * @param info
	 * @param ds
	 * @return
	 */
	public static MigrateTable getTableInfo(SysMigrateInfo info, BaseDBConfig baseDBConfig) {
		MigrateTable tab = new MigrateTable();
		tab.setTabName(info.getMigTabName());
		Map<String, MigrateTableColumn> colMap = new HashMap<String, MigrateTableColumn>();
		Set<String> pkSet = new HashSet<String>();
		try {
			Connection conn = baseDBConfig.getConnection();
			DatabaseMetaData metaData = conn.getMetaData();
			ResultSet rs = metaData.getColumns(null, JDBC_PATTERN, info.getMigTabName(), JDBC_PATTERN);

			while (rs.next()) {
				MigrateTableColumn col = new MigrateTableColumn(rs.getString(COL_COLUMN_NAME), rs.getInt(COL_DATA_TYPE),
						rs.getString(COL_TYPE_NAME));

				colMap.put(col.getColumnName(), col);
			}
			if (colMap.size() == 0) {
				throw new ServiceException("表结构查询异常！");
			}
			ResultSet pkRs = metaData.getPrimaryKeys(conn.getCatalog(), conn.getSchema(), info.getMigTabName());
			while (pkRs.next()) {
				pkSet.add(pkRs.getString(COL_COLUMN_NAME));
			}
		} catch (SQLException | ClassNotFoundException e) {
			throw new ServiceException(e.getMessage());
		}
		if (pkSet.size() > 0) {
			tab.setPks(new ArrayList<MigrateTableColumn>());
		}
		if (colMap.size() - pkSet.size() > 0) {
			tab.setColumns(new ArrayList<MigrateTableColumn>());
		}

		for (String colName : colMap.keySet()) {
			if (pkSet.contains(colName)) {
				tab.getPks().add(colMap.get(colName));
			} else {
				tab.getColumns().add(colMap.get(colName));
			}
		}

		return tab;
	}

	/**
	 * 生成目录节点
	 */
	public static TreeNode getNode(SysMigrateInfo info, BaseDBConfig baseDBConfig) {
		TreeNode node = new TreeNode();
		node.setKey(info.getMigId());
		node.setTitle(info.getMigTabName());
		node.setDisabled(Boolean.FALSE);
		node.setLeaf(Boolean.FALSE);
		node.setValue(info.getMigTabCname());
		node.setChildren(getChildren(info, baseDBConfig));
		return node;
	}

	/**
	 * 获取资源节点，转换node
	 * 
	 * @param info
	 * @return
	 */
	private static List<TreeNode> getChildren(SysMigrateInfo info, BaseDBConfig baseDBConfig) {
		List<TreeNode> nodes = new ArrayList<TreeNode>();
		String sql = getSql(info);
		Connection conn = null;
		Statement stmt = null;
		try {
			conn = baseDBConfig.getConnection();
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				TreeNode node = new TreeNode();
				node.setKey(getTempId(info.getMigId(), rs.getString(MIG_KEY)));
				node.setDisabled(Boolean.FALSE);
				node.setTitle(rs.getString(MIG_VAL));
				node.setLeaf(Boolean.TRUE);
				node.setValue(node.getTitle());
				nodes.add(node);
			}

		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			throw new ServiceException(e.getMessage());
		} finally {
			closeConnection(conn);
			closeStatement(stmt);
		}
		return nodes;
	}

	/**
	 * 导入:按主键判断是否存在
	 * 
	 * @return true/false
	 */
	public static boolean isExistNode(MigrateTable migTab, Map<String, Object> map, String onceSql,
			BaseDBConfig baseDBConfig) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		int count = 0;
		try {
			conn = baseDBConfig.getConnection();
			ps = conn.prepareStatement(onceSql);
			for (int i = 1; i <= migTab.getPks().size(); i++) {
				MigrateTableColumn col = migTab.getPks().get(i - 1);
				DataTypeUtil.setColValue(i, map.get(col.getColumnName()), col.getTypeName(), col.getDataType(), ps);
			}
			rs = ps.executeQuery();
			if (rs.next()) {
				count = rs.getInt(1);
			}
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
			throw new ServiceException(e.getMessage());
		} finally {
			closePreparedStatement(ps);
			closeConnection(conn);
		}
		return count > 0;
	}

	/**
	 * 查询数据
	 */
	public static List<Map<String, Object>> listObject(MigrateTable tab, SysMigrateInfo mig, List<String> ids,
			BaseDBConfig baseDBConfig) {
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		String selectSql = getSelectSql(tab, mig, ids.size());
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = baseDBConfig.getConnection();
			ps = conn.prepareStatement(selectSql);
			for (int index = 1; index <= ids.size(); index++) {
				ps.setString(index, ids.get(index - 1));
			}
			rs = ps.executeQuery();
			while (rs.next()) {
				Map<String, Object> map = new HashMap<String, Object>();
				for (MigrateTableColumn col : tab.getPks()) {
					map.put(col.getColumnName(), rs.getObject(col.getColumnName()));
				}
				for (MigrateTableColumn col : tab.getColumns()) {
					if (DataTypeUtil.isBytes(col.getTypeName())) {// 二进制数据
						map.put(col.getColumnName(), rs.getBytes(col.getColumnName()));
					} else if (DataTypeUtil.isBlob(col.getTypeName())) {// blob-->byte[]
						map.put(col.getColumnName(), rs.getBytes(col.getColumnName()));
					} else {
						map.put(col.getColumnName(), rs.getObject(col.getColumnName()));
					}
				}
				result.add(map);
			}
			return result;
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			throw new ServiceException(e.getMessage());
		} finally {
			closeConnection(conn);
			closePreparedStatement(ps);
			closeResultSet(rs);
		}
	}

	/**
	 * 转换成map,主要为了去重
	 * 
	 * @param tab
	 * @param result
	 * @return
	 */
	public static Map<String, Map<String, Object>> mapObject(MigrateTable tab, List<Map<String, Object>> result) {
		Map<String, Map<String, Object>> resultMap = new HashMap<String, Map<String, Object>>();
		if (result == null || result.size() == 0) {
			return resultMap;
		}
		result.forEach(row -> {
			StringBuffer sb_key = new StringBuffer();
			tab.getPks().forEach(pk -> {
				sb_key.append(row.get(pk.getColumnName())).append("_");
			});
			sb_key.deleteCharAt(sb_key.length() - 1);
			resultMap.put(sb_key.toString(), row);
		});

		return resultMap;
	}

	/**
	 * 批量新增or修改
	 */
	public static void batchInsertOrUpdate(MigrateTable migTab, List<Map<String, Object>> insertList,
			List<Map<String, Object>> updateList, BaseDBConfig baseDBConfig) {
		Connection conn = null;
		try {
			conn = baseDBConfig.getConnection();
			conn.setAutoCommit(false);
			if (insertList.size() > 0) {
				batchInsert(getInsertSql(migTab), insertList, migTab, conn);
			}
			if (updateList.size() > 0) {
				batchUpdate(getUpdateSql(migTab), updateList, migTab, conn);
			}
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
			connRollback(conn);
			throw new ServiceException(e.getMessage());
		} finally {
			closeConnection(conn);
		}
	}

	/**
	 * 批量新增
	 */
	public static void batchInsert(String sql, List<Map<String, Object>> list, MigrateTable migTab, Connection conn) {
		PreparedStatement ps = null;
		try {
			ps = conn.prepareStatement(sql);
			int count = 0;
			// INSERT INTO TABLE (PK1,PK2,COL1,COL2,...) VALUES(?,?,?,?...)
			List<MigrateTableColumn> cols = new ArrayList<MigrateTableColumn>();
			cols.addAll(migTab.getPks());
			cols.addAll(migTab.getColumns());
			for (Map<String, Object> map : list) {
				count++;
				int index = 1;
				for (MigrateTableColumn col : cols) {
					DataTypeUtil.setColValue(index, map.get(col.getColumnName()), col.getTypeName(), col.getDataType(),
							ps);
					index++;
				}
				ps.addBatch();
				if (count % default_submit_num == 1) {// 超过此数量，提交一次
					ps.executeBatch();
				}
			}
			if (count % default_submit_num != 1) {
				ps.executeBatch();
			}
			conn.commit();
		} catch (SQLException e) {
			e.printStackTrace();
			connRollback(conn);
			throw new ServiceException(e.getMessage());
		} finally {
			closePreparedStatement(ps);
		}
	}

	/**
	 * 批量修改
	 */
	public static void batchUpdate(String sql, List<Map<String, Object>> list, MigrateTable migTab, Connection conn) {
		PreparedStatement ps = null;
		try {
			ps = conn.prepareStatement(sql);
			int count = 0;
			// UPDATE TABLE SET PK1=?,COL1=?,PK2=?,COL2=? WHERE PK1=? AND PK2=? ...
			List<MigrateTableColumn> cols = new ArrayList<MigrateTableColumn>();
			cols.addAll(migTab.getPks());
			cols.addAll(migTab.getColumns());
			cols.addAll(migTab.getPks());// 主键条件
			for (Map<String, Object> map : list) {
				count++;
				int index = 1;
				for (MigrateTableColumn col : cols) {
					DataTypeUtil.setColValue(index, map.get(col.getColumnName()), col.getTypeName(), col.getDataType(),
							ps);
					index++;
				}
				ps.addBatch();
				if (count % default_submit_num == 1) {// 超过此数量，提交一次
					ps.executeBatch();
				}
			}
			if (count % default_submit_num != 1) {
				ps.executeBatch();
			}
			conn.commit();
		} catch (SQLException e) {
			e.printStackTrace();
			connRollback(conn);
			throw new ServiceException(e.getMessage());
		} finally {
			closePreparedStatement(ps);
		}
	}

	/**
	 * 获取资源临时id
	 * 
	 * @param migId
	 * @param object
	 * @return
	 */
	private static String getTempId(String migId, Object object) {
		StringBuffer sb = new StringBuffer();
		sb.append(migId).append("_").append(object);
		return sb.toString();
	}

	/**
	 * 获取root节点资源查询sql
	 * 
	 * @description 用于界面展示资源树
	 * @return
	 */
	private static String getSql(SysMigrateInfo info) {
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT ").append(info.getMigColId()).append(" AS ").append(MIG_KEY).append(",");
		sb.append(info.getMigColName()).append(" AS ").append(MIG_VAL).append(" from ");
		sb.append(info.getMigTabName());
		return sb.toString();
	}

	/**
	 * 导出:获取 select sql
	 * 
	 * @return SELECT PK1,PK2,COL1,COL2 FROM TABLE_NAME WHERE mig.getMigId in (?);
	 * @description 查询条件为配置的migId；
	 */
	public static String getSelectSql(MigrateTable tab, SysMigrateInfo mig, int size) {
		if (tab.getPks() == null || tab.getPks().size() == 0) {
			throw new ServiceException("不支持无主键的表");
		}
		StringBuffer sb = new StringBuffer();
		StringBuffer sb_where = new StringBuffer();
		sb.append("SELECT ");
		if (tab.getPks() != null && tab.getPks().size() > 0) {
			tab.getPks().forEach(pk -> {
				sb.append(pk.getColumnName()).append(" ,");
			});
		}
		if (tab.getColumns() != null && tab.getColumns().size() > 0) {
			tab.getColumns().forEach(col -> {
				sb.append(col.getColumnName()).append(" ,");
			});
		}
		sb.deleteCharAt(sb.length() - 1);
		sb.append(" FROM ").append(tab.getTabName());
		sb.append(" WHERE ").append(mig.getMigColId());
		sb.append(" IN (");
		for (int i = 0; i < size; i++) {
			sb.append("?,");
		}
		sb.deleteCharAt(sb.length() - 1).append(")");
		sb.append(sb_where);
		return sb.toString();
	}

	/**
	 * 导入：获取唯一sql
	 * 
	 * @param tab
	 * @return SELECT COUNT(*) FROM TABLE WHERE PK1=? AND PK2=?
	 */
	public static String getOnceSql(MigrateTable tab) {
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT COUNT(*)  FROM ").append(tab.getTabName());
		if (tab.getPks() != null && tab.getPks().size() > 0) {
			sb.append(" WHERE ");
			tab.getPks().forEach(col -> {
				sb.append(col.getColumnName()).append(" =? AND ");
			});
			sb.delete(sb.length() - 4, sb.length());
		}
		return sb.toString();
	}

	/**
	 * 导入：获取 update sql
	 * 
	 * @return UPDATE TABLE SET PK1=?,COL1=?,PK2=?,COL2=? WHERE PK1=? AND PK2=? ...
	 * @description 不支持无主键
	 */
	public static String getUpdateSql(MigrateTable tab) {
		if (tab.getPks() == null || tab.getPks().size() == 0) {
			throw new ServiceException("不支持无主键的表");
		}
		StringBuffer sb = new StringBuffer();
		StringBuffer sb_where = new StringBuffer();
		sb.append("UPDATE ").append(tab.getTabName()).append(" SET ");

		sb_where.append(" WHERE ");
		tab.getPks().forEach(pk -> {
			sb.append(pk.getColumnName()).append(" =?,");
			sb_where.append(pk.getColumnName()).append(" =? AND ");
		});
		sb_where.delete(sb_where.length() - 4, sb_where.length());
		if (tab.getColumns() != null && tab.getColumns().size() > 0) {
			tab.getColumns().forEach(col -> {
				sb.append(col.getColumnName()).append(" =?,");
			});
		}
		sb.deleteCharAt(sb.length() - 1);
		sb.append(sb_where);
		return sb.toString();
	}

	/**
	 * 导入：获取 insert sql
	 * 
	 * @return INSERT INTO TABLE (PK1,PK2,COL1,COL2,...) VALUES(?,?,?,?...)
	 */
	public static String getInsertSql(MigrateTable tab) {
		StringBuffer sb = new StringBuffer();
		StringBuffer sb_val = new StringBuffer();
		sb.append("INSERT INTO ").append(tab.getTabName());
		List<MigrateTableColumn> cols = new ArrayList<MigrateTableColumn>();
		cols.addAll(tab.getPks());
		cols.addAll(tab.getColumns());
		if (cols.size() > 0) {
			sb.append("(");
			sb_val.append(" VALUES(");
			cols.forEach(col -> {
				sb.append(col.getColumnName()).append(" ,");
				sb_val.append("?,");
			});
			sb.deleteCharAt(sb.length() - 1).append(" )");
			sb_val.deleteCharAt(sb_val.length() - 1).append(" )");
		}
		sb.append(sb_val);
		return sb.toString();
	}

	private static void connRollback(Connection conn) {
		try {
			if (conn != null)
				conn.rollback();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private static void closeConnection(Connection conn) {
		try {
			if (conn != null)
				conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private static void closeResultSet(ResultSet rs) {
		try {
			if (rs != null)
				rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private static void closePreparedStatement(PreparedStatement ps) {
		try {
			if (ps != null)
				ps.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private static void closeStatement(Statement stmt) {
		try {
			if (stmt != null)
				stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}