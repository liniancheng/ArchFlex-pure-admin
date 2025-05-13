package com.adtec.rdc.base.user.model.bo;

import java.util.Map;

import lombok.Data;
/**
 * 系统初始化时，加载的资源
 * @author dengchf
 *
 */
@Data
public class SystemCodeBean {
	
	private Map<String, String> codeMap;

}
