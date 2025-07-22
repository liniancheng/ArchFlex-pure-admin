package com.littlelee.base.user.model.bo;

import lombok.Data;

/**
 * 忘记密码配置
 * @author littlelee
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
