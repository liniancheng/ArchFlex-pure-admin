package com.adtec.rdc.base.common.enums;
/**
 * 
 * @author: dengchf     
 * @date:   2020年3月5日 11:04:19   
 * @Description:    TODO数据源相关枚举
 * @version V1.0 
 * @Copyright: adtec
 */
public enum DBLinkEnum {
	//数据源类型
	/** mysql */
	TYPE_MYSQL("1"),
	/** db2 */
	TYPE_DB2("2"),
	/** oracle */
	TYPE_ORACLE("3"),
	//连接池类型
	/** 不使用连接池 */
	POOL_NONE("0"),
	/** 初始化时创建连接池 */
	POOL_START("1"),
	/** 使用时创建连接池 */
	POOL_USED("2"),
	/** 标识：true*/
	FLAG_TRUE("0"),
	/** 标识：false*/
	FLAG_FALSE("1"),
	;
	
	private String type;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	DBLinkEnum(String type) {
		this.type = type;
	}

}
