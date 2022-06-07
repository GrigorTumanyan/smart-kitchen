package com.epam.smartkitchen.service.impl;

import com.epam.smartkitchen.service.MailService;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

@Service
public class SpringMailServiceImpl implements MailService {
    private final MailSender mailSender;

    public SpringMailServiceImpl(MailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Override
    public void sendMail(String clientMail, String subject, String message) {

        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setTo(clientMail);
        simpleMailMessage.setSubject(subject);
        simpleMailMessage.setText(message);
        new Thread(() -> mailSender.send(simpleMailMessage)).start();
    }
}
