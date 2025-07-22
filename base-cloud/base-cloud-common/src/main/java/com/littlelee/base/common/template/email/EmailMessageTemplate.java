package com.littlelee.base.common.template.email;

import java.io.Serializable;

import com.littlelee.base.common.model.bo.email.MailSendBean;
import com.littlelee.base.common.model.bo.email.MailServerBean;
import com.littlelee.base.common.template.MessageTemplate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: littlelee
 * @date: 2018/11/30 17:18
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class EmailMessageTemplate extends MessageTemplate implements Serializable {
	private static final long serialVersionUID = 970496754360740292L;
	
	private MailServerBean mailServer;
	private MailSendBean mailSend;
}
