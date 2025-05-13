package com.adtec.moia.base.engine.database.dialect;

import com.adtec.moia.base.engine.database.DatabaseException;

public class MySQLDialect extends Dialect {
	@Override
	public String getPageSql(String sql, int pageSize, int startRow) throws DatabaseException {
		StringBuffer sb = new StringBuffer();
		sb.append("select A_TEMP.* from ");
		if (sql.toLowerCase().indexOf(" from ") > 0)
			sb.append("(").append(sql).append(")");
		else
			sb.append(sql);
		sb.append(" as A_TEMP LIMIT ");
		sb.append(startRow).append(",");
		sb.append(pageSize);
		return sb.toString();
	}
}
