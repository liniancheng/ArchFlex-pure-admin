package com.littlelee.base.message.handler.email;

import com.littlelee.base.common.model.bo.email.MailSendBean;
import com.littlelee.base.common.model.bo.email.MailServerBean;
import com.littlelee.base.common.template.MessageTemplate;
import com.littlelee.base.common.template.email.EmailMessageTemplate;
import com.littlelee.base.message.log.service.NoticeMailSendLogService;
import com.littlelee.base.message.log.util.LogUtils;
import com.littlelee.base.message.util.ExchangeUtils;
import com.littlelee.base.message.util.MailUtils;

import lombok.extern.slf4j.Slf4j;

import java.util.Date;
import java.util.List;
import java.util.Properties;

import jakarta.mail.Address;
import jakarta.mail.BodyPart;
import jakarta.mail.Message;
import jakarta.mail.Multipart;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.AddressException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeBodyPart;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeMultipart;
import jakarta.mail.internet.MimeUtility;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 邮件发送处理
 * @author littlelee
 *
 */
@Slf4j
@Component
public class EmailSpringJavaMailMessageHandler extends EmailMessageHandler {
	@Autowired
    private NoticeMailSendLogService logService;
	
    @Override
    public Boolean execute(MessageTemplate messageTemplate) {
        String errorMsg = null;
        MailServerBean server = null;
        MailSendBean send = null;
    	try {
        	EmailMessageTemplate emailMessageTemplate = (EmailMessageTemplate) messageTemplate;
        	server = emailMessageTemplate.getMailServer();
        	send = emailMessageTemplate.getMailSend();
        	if(isExchangeServer(server)) {
        		ExchangeUtils.sendMail(server, send);
        	}else {
        		//设置属性
            	Properties prop = getProperties(server);
            	//session
            	Session mailSession = Session.getInstance(prop);
            	mailSession.setDebug(true);//debug
            	//收件信息
            	Message mailMessage = getMessage(mailSession, server, send);
            	//邮件内容
                Multipart mainPart = new MimeMultipart();
                BodyPart html = new MimeBodyPart();
                html.setContent(MailUtils.getContent(send.getContent(), send.getParaMap()), "text/html; charset=utf-8");
                mainPart.addBodyPart(html);
                mailMessage.setContent(mainPart);
                //发送邮件
                Transport transport = mailSession.getTransport();
                transport.connect(server.getLoginName(), server.getLoginPwd());
                transport.sendMessage(mailMessage, mailMessage.getAllRecipients());
                transport.close();
        	}
            log.info("邮件发送成功,服务器:{}, 邮件:{}", server.getSrvName(), send.getTitle());
        } catch (Exception e){
        	e.printStackTrace();
            errorMsg = e.getMessage();
        	if(errorMsg == null) {
        		errorMsg = "未知错误";
        	}
        	log.error("邮件发送失败：{}", e.getMessage());
            return false;
        } finally {
        	logService.save(LogUtils.getMailLog(server, send, errorMsg));
        }
        return true;
    }
    
	private boolean isExchangeServer(MailServerBean server) {
		return server.getSrvUrl().indexOf("Exchange.asmx")>-1;
	}

	private Message getMessage(Session mailSession, MailServerBean server, MailSendBean send) throws Exception {
    	Message mailMessage = new MimeMessage(mailSession);
    	String showName = MimeUtility.encodeText(server.getShowName());
        Address from = new InternetAddress(showName + " <" + server.getDefaultFrom() + ">"); 
        mailMessage.setFrom(from);
        //收件地址
        mailMessage.setRecipients(Message.RecipientType.TO, getAddress(send.getToMails()));
        //抄送地址
        if(send.getCcMails() != null && send.getCcMails().size()>0) {
        	mailMessage.setRecipients(Message.RecipientType.CC, getAddress(send.getCcMails()));
        }
        //密送地址
        if(send.getBccMails() != null && send.getBccMails().size()>0) {
        	mailMessage.setRecipients(Message.RecipientType.BCC, getAddress(send.getBccMails()));
        }
        mailMessage.setSubject(send.getTitle());
        mailMessage.setSentDate(new Date());
        return mailMessage;
	}

	private static Address[] getAddress(List<String> mails) throws AddressException {
    	Address[] toList = new InternetAddress[mails.size()];
    	for(int i=0; i<mails.size(); i++) {
    		Address address = new InternetAddress(mails.get(i));
    		toList[i] = address;
    	}
    	return toList;
    }
    
	private static Properties getProperties(MailServerBean server) {
        Properties prop = new Properties();
        prop.put("mail.smtp.host", server.getSrvUrl());  
        prop.put("mail.smtp.port", server.getSrvPort());  
        prop.put("mail.smtp.auth", "true");
        // 开启ssl
        if (server.isSslFlag()) {
            prop.put("mail.smtp.socketFactory.port", server.getSrvPort());  
            prop.put("mail.smtp.socketFactory.class", "jakarta.net.ssl.SSLSocketFactory");
        }
        return prop;
    }
}
