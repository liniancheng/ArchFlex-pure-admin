package com.adtec.rdc.base.user.model.bo;

import lombok.Data;

/**
 * 忘记密码配置
 * @author JTao
 *
 */
@Data
public class ForgetPasswordBean {
	private String appId;
	private String mailSrvName;
	private String mailTempName;
	private String smsSrvName;
	private String smsTempName;
}
