package com.quickmove.GoFaster.service;
//++import javamail.JavaMailSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MailService {

    @Autowired
    private JavaMailSender javaMailSender;

    public void sendMail(String toMail, String subject, String message) {

        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setTo(toMail);
        mail.setSubject(subject);
        mail.setText(message != null ? message : "No content");

        javaMailSender.send(mail);
    }
}

