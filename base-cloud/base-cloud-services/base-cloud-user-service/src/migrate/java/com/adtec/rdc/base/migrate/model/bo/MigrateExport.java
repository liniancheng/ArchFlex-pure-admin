package com.adtec.rdc.base.migrate.model.bo;

import java.io.Serializable;
import java.util.List;

/**
 *	导出的数据对象
 * @author dengchf
 *
 */
public class MigrateExport implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -514089814305307242L;
	private List<MigrateTable> lists;
	
	public List<MigrateTable> getLists() {
		return lists;
	}

	public void setLists(List<MigrateTable> lists) {
		this.lists = lists;
	}
	
	public MigrateExport() {
	}

	public MigrateExport(List<MigrateTable> lists) {
		super();
		this.lists = lists;
	}

	@Override
	public String toString() {
		return "MigrateList [lists=" + lists + "]";
	}
}
