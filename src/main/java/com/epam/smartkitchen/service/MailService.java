package com.epam.smartkitchen.service;

public interface MailService {
    void sendMail(String clientMail, String subject, String message);
}
