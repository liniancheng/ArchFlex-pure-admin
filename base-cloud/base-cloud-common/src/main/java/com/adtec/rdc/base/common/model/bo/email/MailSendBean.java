package com.adtec.rdc.base.common.model.bo.email;

import java.util.List;
import java.util.Map;

import lombok.Data;

/**
 * 邮件发送bean
 * @author JTao
 *
 */
@Data
public class MailSendBean {
	private String tempId;					//模板ID
	private String title;					//主题
	private String content;					//内容(html,可含参数)
	private Map<String, Object> paraMap;	//参数
	private List<String> toMails;			//收件地址s
	private List<String> ccMails;			//抄送地址s
	private List<String> bccMails;			//密送地址s
	private String loginName;	//登录用户,系统发送则为system
}
