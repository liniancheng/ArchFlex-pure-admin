package com.adtec.rdc.base.migrate.model.bo;

import java.io.Serializable;

/**
 * 迁移的字段属性
 * @author dengchf
 * @description
 * 		英文名、字段类型、类型名称
 *
 */
public class MigrateTableColumn  implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 4771878636709657049L;
	/**
     * 列名
     */
    private String columnName;
    /**
     * 字段类型
     */
    private Integer dataType;
    /**
     * 字段key类型
     */
    private String typeName;
	public String getColumnName() {
		return columnName;
	}
	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}
	public Integer getDataType() {
		return dataType;
	}
	public void setDataType(Integer dataType) {
		this.dataType = dataType;
	}
	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	public MigrateTableColumn() {
	}
	public MigrateTableColumn(String columnName, Integer dataType, String typeName) {
		super();
		this.columnName = columnName;
		this.dataType = dataType;
		this.typeName = typeName;
	}
	@Override
	public String toString() {
		return "MigrateTableColumn [columnName=" + columnName + ", dataType=" + dataType + ", typeName=" + typeName
				+ "]";
	}
	
}
