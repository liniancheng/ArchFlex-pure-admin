package com.adtec.rdc.base.dict.model.vo;

import java.io.Serializable;

import lombok.Data;

@Data
public class DictModel implements Serializable{
	private static final long serialVersionUID = 1L;

	public DictModel() {
	}
	
	public DictModel(String value, String text) {
		this.value = value;
		this.text = text;
	}
	
	/**
	 * 字典value
	 */
	private String value;
	/**
	 * 字典文本
	 */
	private String text;
}
