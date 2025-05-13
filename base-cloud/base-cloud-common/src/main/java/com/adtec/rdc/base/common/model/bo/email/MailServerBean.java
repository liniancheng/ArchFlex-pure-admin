package com.adtec.rdc.base.common.model.bo.email;

import lombok.Data;

/**
 * 邮件服务器bean
 * @author JTao
 *
 */
@Data
public class MailServerBean {
	private String srvId;		//服务ID
	private String srvName;		//服务名
	private String loginName;	//用户
    private String loginPwd;	//密码
    private String srvUrl;		//服务URL
    private String defaultFrom; //发送邮箱
    private String showName;	//显示名
    private int srvPort;		//服务端口
    private boolean sslFlag; 	//ssl标志
}
