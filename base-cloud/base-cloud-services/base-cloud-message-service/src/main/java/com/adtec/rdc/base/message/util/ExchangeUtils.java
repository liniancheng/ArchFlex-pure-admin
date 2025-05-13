package com.adtec.rdc.base.message.util;

import com.adtec.rdc.base.common.model.bo.email.MailSendBean;
import com.adtec.rdc.base.common.model.bo.email.MailServerBean;
import com.adtec.rdc.base.message.handler.email.exchange.ExchangeClient;

import microsoft.exchange.webservices.data.core.enumeration.misc.ExchangeVersion;

public class ExchangeUtils {
	
	public static void sendMail(MailServerBean server, MailSendBean send) throws Exception {
		ExchangeClient exchangeClient = new ExchangeClient.ExchangeClientBuilder()
				.hostname(server.getSrvUrl())
				.exchangeVersion(ExchangeVersion.Exchange2010)
				.username(server.getLoginName())
				.password(server.getLoginPwd())
				.subject(send.getTitle())
				.message(MailUtils.getContent(send.getContent(), send.getParaMap()))
				.recipientTo(send.getToMails())
				.recipientCc(send.getCcMails())
				.recipientBcc(send.getBccMails())
				.build();
		exchangeClient.sendExchangeMail();
	}
}
