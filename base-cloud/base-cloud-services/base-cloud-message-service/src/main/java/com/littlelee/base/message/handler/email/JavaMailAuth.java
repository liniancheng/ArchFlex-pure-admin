package com.littlelee.base.message.handler.email;

import jakarta.mail.Authenticator;
import jakarta.mail.PasswordAuthentication;

/**
 * 授权码认证
 * @author littlelee
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
