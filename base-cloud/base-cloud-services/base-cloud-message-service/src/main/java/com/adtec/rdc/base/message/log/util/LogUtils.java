package com.adtec.rdc.base.message.log.util;

import java.time.LocalDateTime;

import com.adtec.rdc.base.common.model.bo.email.MailSendBean;
import com.adtec.rdc.base.common.model.bo.email.MailServerBean;
import com.adtec.rdc.base.common.util.UUID;
import com.adtec.rdc.base.message.log.model.po.NoticeMailSendLog;

public class LogUtils {

	public static NoticeMailSendLog getMailLog(MailServerBean server, MailSendBean send, String errorMsg) {
		NoticeMailSendLog log = new NoticeMailSendLog();
		log.setCreateTime(LocalDateTime.now());
		log.setLogId(UUID.generate());
		log.setLoginName(send.getLoginName()==null?"":send.getLoginName());
		if(errorMsg == null) {
			log.setLogState("1");
		}else {
			log.setLogState("0");
			log.setErrorStr(errorMsg);
		}
		log.setSrvId(server.getSrvId());
		log.setTempId(send.getTempId());
		return log;
	}
	
}
