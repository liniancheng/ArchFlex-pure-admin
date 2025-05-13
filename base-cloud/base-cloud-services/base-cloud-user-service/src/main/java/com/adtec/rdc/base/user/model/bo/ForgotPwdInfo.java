package com.adtec.rdc.base.user.model.bo;

import java.io.Serializable;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 忘记密码工具类
 * 
 * @author dengchf
 *
 */
@Data
@Accessors(chain = true)
public class ForgotPwdInfo  implements Serializable{

	private static final long serialVersionUID = 8907200732056111640L;
	
	public static final String FORGOT_PWD_TYPE_MOBILE = "mobile";
	public static final String FORGOT_PWD_TYPE_EMAIL = "email";

	private String type;// 找回类型：邮箱/手机
	private String loginName;
	private String userEmail;
	private String password;
	private String password2;
	private String userMobTel;
	private String smsCaptcha;
}
