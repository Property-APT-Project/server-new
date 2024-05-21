package com.home.service;

import jakarta.mail.MessagingException;

public interface EmailService {
    public void sendEmail(String to, String subject, String text) throws MessagingException;

    public void sendPasswordResetEmail(String to, String token) throws MessagingException;
}
