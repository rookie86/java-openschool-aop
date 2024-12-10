package com.openschool.aop.service;

import org.springframework.stereotype.Component;

@Component
public class NotificationService {

    private final EmailService emailService;

    public void sendEmailNotification(String to, String subject, String body) {
        emailService.sendSimpleMessage("noreply@openschool.testproject", to, subject, body);
    }

    public NotificationService(EmailService emailService) {
        this.emailService = emailService;
    }
}
