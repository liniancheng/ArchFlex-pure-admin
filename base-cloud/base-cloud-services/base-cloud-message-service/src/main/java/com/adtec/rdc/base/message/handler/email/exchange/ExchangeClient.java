package com.adtec.rdc.base.message.handler.email.exchange;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import microsoft.exchange.webservices.data.core.ExchangeService;
import microsoft.exchange.webservices.data.core.enumeration.misc.ExchangeVersion;
import microsoft.exchange.webservices.data.core.exception.service.local.ServiceLocalException;
import microsoft.exchange.webservices.data.core.service.item.EmailMessage;
import microsoft.exchange.webservices.data.credential.ExchangeCredentials;
import microsoft.exchange.webservices.data.credential.WebCredentials;
import microsoft.exchange.webservices.data.property.complex.MessageBody;

public class ExchangeClient {
	private final String hostname;
    private final ExchangeVersion exchangeVersion;
    private final String domain;
    private final String username;
    private final String password;
    private final String subject;
    private final List<String> recipientTo;
    private final List<String> recipientCc;
    private final List<String> recipientBcc;
    private final List<String> attachments;
    private final String message;

    private ExchangeClient(ExchangeClientBuilder builder) {
        this.hostname = builder.hostname;
        this.exchangeVersion = builder.exchangeVersion;
        this.domain = builder.domain;
        this.username = builder.username;
        this.password = builder.password;
        this.subject = builder.subject;
        this.recipientTo = builder.recipientTo;
        this.recipientCc = builder.recipientCc;
        this.recipientBcc = builder.recipientBcc;
        this.attachments = builder.attachments;
        this.message = builder.message;
    }
    public static class ExchangeClientBuilder {
    	private String hostname;//Exchange Web服务的主机名
        private ExchangeVersion exchangeVersion;
        private String domain;
        private String username;
        private String password;
        private String subject;
        private List<String> recipientTo;
        private List<String> recipientCc;
        private List<String> recipientBcc;
        private List<String> attachments;
        private String message;
        
        public ExchangeClientBuilder() {
            this.exchangeVersion = ExchangeVersion.Exchange2010_SP1;
            this.hostname = "";
            this.username = "";
            this.password = "";
            this.subject = "";
            this.recipientTo = new ArrayList<>(0);
            this.recipientCc = new ArrayList<>(0);
            this.recipientBcc = new ArrayList<>(0);
            this.attachments = new ArrayList<>(0);
            this.message = "";
        }

        /**
         * @Desc Exchange Web服务的主机名
         * @param hostname
         * @return
         */
        public ExchangeClientBuilder hostname(String hostname) {
            this.hostname = hostname;
            return this;
        }

        /**
         * @Desc Exchange Web服务器版本。
         * @param exchangeVersion
         * @return
         */
        public ExchangeClientBuilder exchangeVersion(ExchangeVersion exchangeVersion) {
            this.exchangeVersion = exchangeVersion;
            return this;
        }

        /**
         * @Desc smtp服务器的域。
         * @param domain
         * @return
         */
        public ExchangeClientBuilder domain(String domain) {
            this.domain = domain;
            return this;
        }

        /**
         * @Desc MS Exchange Smtp服务器的用户名 发送用户
         * @param username
         * @return
         */
        public ExchangeClientBuilder username(String username) {
            this.username = username;
            return this;
        }

        /**
         * @Desc 发送用户的密码
         * @param password
         * @return
         */
        public ExchangeClientBuilder password(String password) {
            this.password = password;
            return this;
        }

        /**
         * @Desc 邮件主题
         * @param subject
         * @return
         */
        public ExchangeClientBuilder subject(String subject) {
            this.subject = subject;
            return this;
        }

        /**
         * @Desc 收件人
         * @param recipientTo
         * @return
         */
        public ExchangeClientBuilder recipientTo(List<String> recipientTo) {
            this.recipientTo = recipientTo;
            return this;
        }

        /**
         * @Desc 抄送人
         * @param recipientCc
         * @param recipientsCc
         * @return
         */
        public ExchangeClientBuilder recipientCc(String recipientCc, String... recipientsCc) {
            // Prepare the list.
            List<String> recipients = new ArrayList<>(1 + recipientsCc.length);
            recipients.add(recipientCc);
            recipients.addAll(Arrays.asList(recipientsCc));
            // Set the list.
            this.recipientCc = recipients;
            return this;
        }

        /**
         * @Desc 抄送人
         * @param recipientCc
         * @return
         */
        public ExchangeClientBuilder recipientCc(List<String> recipientCc) {
            this.recipientCc = recipientCc;
            return this;
        }

        /**
         * @Desc 密送人
         * @param recipientBcc
         * @param recipientsBcc
         * @return
         */
        public ExchangeClientBuilder recipientBcc(String recipientBcc, String... recipientsBcc) {
            // Prepare the list.
            List<String> recipients = new ArrayList<>(1 + recipientsBcc.length);
            recipients.add(recipientBcc);
            recipients.addAll(Arrays.asList(recipientsBcc));
            // Set the list.
            this.recipientBcc = recipients;
            return this;
        }

        /**
         * @Desc 密送人
         * @param recipientBcc
         * @return
         */
        public ExchangeClientBuilder recipientBcc(List<String> recipientBcc) {
            this.recipientBcc = recipientBcc;
            return this;
        }

        /**
         * @Desc 附件
         * @param attachment
         * @param attachments
         * @return
         */
        public ExchangeClientBuilder attachments(String attachment, String... attachments) {
            // Prepare the list.
            List<String> attachmentsToUse = new ArrayList<>(1 + attachments.length);
            attachmentsToUse.add(attachment);
            attachmentsToUse.addAll(Arrays.asList(attachments));
            // Set the list.
            this.attachments = attachmentsToUse;
            return this;
        }

        /**
         * @Desc 附件
         * @param attachments
         * @return
         */
        public ExchangeClientBuilder attachments(List<String> attachments) {
            this.attachments = attachments;
            return this;
        }

        /**
         * @Desc 邮件正文
         * @param message
         * @return
         */
        public ExchangeClientBuilder message(String message) {
            this.message = message;
            return this;
        }

        /**
         * @Desc 创建邮件
         * @return
         */
        public ExchangeClient build() {
            return new ExchangeClient(this);
        }
    }
    
    /**
     * @Desc 发送邮件
     * @return
     * @throws Exception 
     */
    public String sendExchangeMail() throws Exception {
        // Exchange服务器版本。
        ExchangeService exchangeService = new ExchangeService(exchangeVersion);

        // 要在MS Exchange服务器上签名的凭据。
        ExchangeCredentials exchangeCredentials = new WebCredentials(username, password, domain);
        exchangeService.setCredentials(exchangeCredentials);

        // 邮箱的exchange web服务的URL
        try {
            exchangeService.setUrl(new URI(hostname));
        } catch (URISyntaxException ex) {
            if(exchangeService!=null) {
            	exchangeService.close();
            }
            throw new Exception("创建与服务端的连接发生异常");
        }

        // 设置邮件信息
        EmailMessage emailMessage;
        try {
            emailMessage = new EmailMessage(exchangeService);
            emailMessage.setSubject(subject);
            emailMessage.setBody(MessageBody.getMessageBodyFromText(message));
        } catch (Exception ex) {
        	throw new Exception("设置邮件发生异常");
        }

        if(recipientCc != null) {
	        // 设置收件人
	        try {
	        	for(String to : recipientTo) {
	        		emailMessage.getToRecipients().add(to);
	        	}
	        } catch (ServiceLocalException ex) {
	            throw new Exception("设置邮件收件人发生异常");
	        }
        }

        if(recipientCc != null) {
        	// 设置抄送人
            for (String recipient : recipientCc) {
                try {
                    emailMessage.getCcRecipients().add(recipient);
                } catch (ServiceLocalException ex) {
                	throw new Exception("设置邮件抄送人发生异常");
                }
            }
        }

        if(recipientBcc != null) {
	        // 设置邮件密送人
	        for (String recipient : recipientBcc) {
	            try {
	                emailMessage.getBccRecipients().add(recipient);
	            } catch (ServiceLocalException ex) {
	                throw new Exception("设置邮件密送人发生异常");
	            }
	        }
        }

        if(attachments != null) {
        	// 设置附件
            for (String attachmentPath : attachments) {
                try {
                    emailMessage.getAttachments().addFileAttachment(attachmentPath);
                } catch (ServiceLocalException ex) {
                	throw new Exception("设置邮件附件发生异常");
                }
            }
        }

        try {
            emailMessage.send();
        } catch (Exception ex) {
        	throw new Exception("邮件发送异常");
        }
        return null;
    }
}
