package com.adtec.rdc.base.migrate.enums;
/**
 * 
 * @author dengchf
 * @description: 关联表，关联字段解析类型枚举
 *
 */
public enum MigrateConverTypeEnum {
	/**
	 * 默认，不解析
	 */
	DEFAULT("0"),
	/**
	 * json/bean.toString()
	 */
	JSON("1"),
	/**
	 * list.toString()
	 */
	LIST("2"),
	/**
	 * map.toString()
	 */
	MAP("3");
	
	private String val;

	
	public String getVal() {
		return val;
	}


	public void setVal(String val) {
		this.val = val;
	}


	MigrateConverTypeEnum(String val){
		this.val = val;
	}

}
