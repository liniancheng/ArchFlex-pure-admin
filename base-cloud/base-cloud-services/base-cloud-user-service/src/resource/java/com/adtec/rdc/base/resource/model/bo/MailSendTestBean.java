package com.adtec.rdc.base.resource.model.bo;

import lombok.Data;

@Data
public class MailSendTestBean {
	private String srvId;
	private String tempId;
	private String title;
	private String content;
	private String toMails;//逗号分隔
	private String ccMails;//逗号分隔
	private String bccMails;//逗号分隔
	private String paraValues;//逗号分隔,name与value等号分隔
	private String loginName;
}
