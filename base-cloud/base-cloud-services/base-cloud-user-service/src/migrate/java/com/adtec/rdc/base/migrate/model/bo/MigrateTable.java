package com.adtec.rdc.base.migrate.model.bo;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * 迁移的表结构以及数据
 * 
 * @author dengchf
 * @description 导出：表明、主键信息，非主键信息，数据
 *
 */
public class MigrateTable implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3967228173428649573L;
	private String tabName;
	private List<MigrateTableColumn> pks;
	private List<MigrateTableColumn> columns;
	private List<Map<String, Object>> datas;

	public String getTabName() {
		return tabName;
	}

	public void setTabName(String tabName) {
		this.tabName = tabName;
	}

	public List<MigrateTableColumn> getColumns() {
		return columns;
	}

	public void setColumns(List<MigrateTableColumn> columns) {
		this.columns = columns;
	}

	public List<MigrateTableColumn> getPks() {
		return pks;
	}

	public void setPks(List<MigrateTableColumn> pks) {
		this.pks = pks;
	}

	public List<Map<String, Object>> getDatas() {
		return datas;
	}

	public void setDatas(List<Map<String, Object>> datas) {
		this.datas = datas;
	}

	public MigrateTable() {
	}

	public MigrateTable(String tabName, List<MigrateTableColumn> pks, List<MigrateTableColumn> columns,
			List<Map<String, Object>> datas) {
		super();
		this.tabName = tabName;
		this.pks = pks;
		this.columns = columns;
		this.datas = datas;
	}

	@Override
	public String toString() {
		return "MigrateTable [tabName=" + tabName + ", pks=" + pks + ", columns=" + columns + ", datas=" + datas + "]";
	}

}
