package com.adtec.rdc.web.antd.bo;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author: dengchf
 * @date: 2020年8月27日
 * @Description: 穿越框节点
 * @version V1.0
 * @Copyright: adtec
 */
@Data
@Accessors(chain = true)
public class TransferNode {
	/**
	 * 主键
	 */
	private String key;
	/**
	 * 显示内容
	 */
	private String title;
	/**
	 * 是否可选
	 */
	private boolean disabled;
	/**
	 * 是否选中
	 */
	private boolean checked;

}
