package com.adtec.rdc.base.resource.utils;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.adtec.rdc.base.common.model.bo.email.MailSendBean;
import com.adtec.rdc.base.common.model.bo.email.MailServerBean;
import com.adtec.rdc.base.common.util.DateUtil;
import com.adtec.rdc.base.resource.model.bo.MailSendTestBean;
import com.adtec.rdc.base.resource.model.po.NoticeMailSrvInfo;
import com.adtec.rdc.base.resource.model.po.NoticeMailTempInfo;

import io.micrometer.core.instrument.util.StringUtils;

/**
 * 邮件相关工具类
 * @author JTao
 *
 */
public class MailUtils {

	public static MailServerBean getMailServer(NoticeMailSrvInfo mailSrvInfo) {
		MailServerBean bean = new MailServerBean();
		bean.setSrvId(mailSrvInfo.getSrvId());
		bean.setDefaultFrom(mailSrvInfo.getDefaultFrom());
		bean.setLoginName(mailSrvInfo.getLoginName());
		bean.setLoginPwd(mailSrvInfo.getLoginPwd());
		bean.setShowName(mailSrvInfo.getShowName());
		bean.setSrvName(mailSrvInfo.getSrvName());
		bean.setSrvPort(mailSrvInfo.getSrvPort());
		bean.setSrvUrl(mailSrvInfo.getSrvUrl());
		bean.setSslFlag("1".equals(mailSrvInfo.getSslFlag()));
		return bean;
	}

	public static MailSendBean getMailSend(MailSendTestBean sendTest, NoticeMailTempInfo mailTempInfo) {
		MailSendBean bean= new MailSendBean();
		bean.setLoginName(sendTest.getLoginName());
		bean.setTempId(mailTempInfo.getTempId());
		if(!StringUtils.isEmpty(sendTest.getBccMails())) {
			bean.setBccMails(Arrays.asList(sendTest.getBccMails().split(",")));
		}
		if(!StringUtils.isEmpty(sendTest.getCcMails())) {
			bean.setCcMails(Arrays.asList(sendTest.getCcMails().split(",")));
		}
		bean.setContent(mailTempInfo.getTempContent());
		Map<String, Object> paraMap = new HashMap<String, Object>();
		paraMap.put("SEND_DATE", DateUtil.format(new Date(), "yyyy-MM-dd"));
		if(!StringUtils.isEmpty(sendTest.getParaValues())) {
			String[] arrayParas = sendTest.getParaValues().split(",");
			for(String arrayPara : arrayParas) {
				String[] paras = arrayPara.split("=");
				if(paras.length==2) {
					paraMap.put(paras[0], paras[1]);
				}
			}
		}
		bean.setParaMap(paraMap);
		bean.setTitle(sendTest.getTitle());
		bean.setToMails(Arrays.asList(sendTest.getToMails().split(",")));
		return bean;
	}

}
