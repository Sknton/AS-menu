package com.as.spring.asmenu.service.mail;

public interface MailSender {
    void send(String emailTo, String subject, String message);
}
