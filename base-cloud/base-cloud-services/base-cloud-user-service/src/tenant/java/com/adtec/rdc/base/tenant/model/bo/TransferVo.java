package com.adtec.rdc.base.tenant.model.bo;

import lombok.Data;
/**
 * 穿梭框
 * @author liushp
 */
@Data
public class TransferVo {
	/**
	 * 主键
	 */
	private String key;
	/**
	 * 名称
	 */
	private String title;
	/**
	 * 是否选中
	 */
	private String chosen;
	
}
