package com.adtec.rdc.base.common.enums;

/**
 * @author: lsp
 * @description: 机构枚举
 */
public enum BranchEnums {
	
	// 机构类型
    TYPE_P("P", "TYPE_P"),
    TYPE_A("A", "TYPE_A"),
	TYPE_B("B", "TYPE_B"),
	TYPE_C("C", "TYPE_C"),
	TYPE_D("D", "TYPE_D"),
	TYPE_E("E", "TYPE_E"),
	//ETL同步
	ETL("0", "是"),
	NOTETL("1", "否"),
	//是否是虚拟机构
	VIR("0", "是"),
	NOTVIR("1", "否"),
	//虚拟字段，删除标识.
	DEL_NORMAL("0", "正常"),
	DEL_DELETE("1", "删除");
	
    private String code;
    
    private String desc;


    BranchEnums(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public String getCode() {
        return code;
    }

	public String getDesc() {
		return desc;
	}
    
}
