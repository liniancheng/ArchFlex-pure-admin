package com.adtec.rdc.web.antd.bo;

import java.util.List;

import lombok.Data;

/**
 * 表单输入 项
 * @author JTao
 *
 */
@Data
public class Input {
	/**
     * 标签
     */
	private String label;
	/**
     * 名称
     */
	private String name;
	/**
     * 类型
     */
	private String type;
	/**
     * 值
     */
	private String value;
	/**
     * 是否必输
     */
	private boolean required;
	/**
	 * 选项
	 */
	private List<SelectOption> options;
	/**
     * 不可输入
     */
	private boolean disabled;
}
