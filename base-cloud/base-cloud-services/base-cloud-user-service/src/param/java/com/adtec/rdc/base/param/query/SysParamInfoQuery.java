package com.adtec.rdc.base.param.query;

import java.time.LocalDateTime;

import com.adtec.rdc.base.param.model.po.SysParamInfo;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import lombok.Data;

@Data
public class SysParamInfoQuery extends Page<SysParamInfo>{/**
	 * 
	 */
	private static final long serialVersionUID = -7285382555179628300L;
	
	private String paramId;
	
	private String paramName;
	
	private String paramValue;
	
	private String paramRmk;
	
	private String appId;
	
	private LocalDateTime createTime;
	
	private LocalDateTime modifyTime;
	
	private String adminAppId;
}
