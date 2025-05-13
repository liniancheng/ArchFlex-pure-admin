package com.adtec.rdc.base.migrate.enums;
/**
 * @author dengchf
 * @description	表数据类型枚举
 *
 */
public enum MigrateTypeEnum {
	/** 目录*/
	DIR("0"),
	/** 主表*/
	TAB("1"),
	/** 关系表*/
	REL("2");
	
	private String type;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	MigrateTypeEnum(String type){
		this.type = type;
	}

}
