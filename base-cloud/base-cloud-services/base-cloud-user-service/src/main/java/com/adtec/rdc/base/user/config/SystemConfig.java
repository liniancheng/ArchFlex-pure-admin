package com.adtec.rdc.base.user.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.adtec.rdc.base.user.model.bo.SystemConfigBean;
import com.adtec.rdc.base.user.model.bo.ForgetPasswordBean;

/**
 * 系统定制配置
 * @author JTao
 *
 */
@Configuration
public class SystemConfig {
	@Value("${system.forget-password.app-id}")
	private String forgetPasswordAppId;
	@Value("${system.forget-password.mail-server-name}")
	private String forgetPasswordMailServerName;
	@Value("${system.forget-password.mail-template-name}")
	private String forgetPasswordMailTemplateName;
	@Value("${system.forget-password.sms-server-name}")
	private String forgetPasswordSmsServerName;
	@Value("${system.forget-password.sms-template-name}")
	private String forgetPasswordSmsTemplateName;
	
	@Bean
    public SystemConfigBean systemConfigBean(){
		SystemConfigBean config = new SystemConfigBean();
		ForgetPasswordBean forgetPasswordBean = new ForgetPasswordBean();
		forgetPasswordBean.setAppId(forgetPasswordAppId);
		forgetPasswordBean.setMailSrvName(forgetPasswordMailServerName);
		forgetPasswordBean.setMailTempName(forgetPasswordMailTemplateName);
		forgetPasswordBean.setSmsSrvName(forgetPasswordSmsServerName);
		forgetPasswordBean.setSmsTempName(forgetPasswordSmsTemplateName);
		config.setForgetPasswordBean(forgetPasswordBean);
        return config;
    }
}
