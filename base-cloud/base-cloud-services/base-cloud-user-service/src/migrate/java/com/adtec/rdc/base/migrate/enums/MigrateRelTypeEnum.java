package com.adtec.rdc.base.migrate.enums;
/**
 * @author dengchf
 * @description	表数据类型枚举
 *
 */
public enum MigrateRelTypeEnum {
	/** 主表关联关系表*/
	_PARENT("0"),
	/** 关系表关联主表*/
	_CHLID("1"),
	/** 自关联*/
	_SELF("2");
	
	private String type;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	MigrateRelTypeEnum(String type){
		this.type = type;
	}

}
