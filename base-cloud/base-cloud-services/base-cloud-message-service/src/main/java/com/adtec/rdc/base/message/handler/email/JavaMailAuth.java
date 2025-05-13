package com.adtec.rdc.base.message.handler.email;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

/**
 * 授权码认证
 * @author JTao
 *
 */
public class JavaMailAuth extends Authenticator {
    private String username;
    private String password;

    public JavaMailAuth(String username, String password) {
        this.username = username;
        this.password = password;
    }

    @Override
    protected PasswordAuthentication getPasswordAuthentication() {
        return new PasswordAuthentication(username, password);
    }
}
