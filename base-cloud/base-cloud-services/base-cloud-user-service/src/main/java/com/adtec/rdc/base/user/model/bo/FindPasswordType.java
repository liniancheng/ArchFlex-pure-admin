package com.adtec.rdc.base.user.model.bo;

import lombok.Data;

/**
 * 找回密码类型
 * @author JTao
 *
 */
@Data
public class FindPasswordType {
	private boolean mailType;//通过邮件找回-需要在yml中配置邮件服务器
	private boolean phoneType;//通过短信找回-需要在yml中配置短信服务器
	private boolean adminType;//管理员找回
	private String adminUserName;
	private String adminUserEmail;
	private String adminUserPhone;	
}
