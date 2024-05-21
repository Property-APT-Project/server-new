package com.home.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Value("${email.from}")
    private String fromEmail;

    public void sendEmail(String to, String subject, String text) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

        helper.setFrom(fromEmail);
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(text, true);

        mailSender.send(message);
    }

    @Async
    public void sendPasswordResetEmail(String to, String token) throws MessagingException {
        String subject = "[Home] 비밀번호 변경 인증 메일";
        String resetUrl = "http://localhost:5173/confirm-reset?token=" + token;
        String content = "<div style=\"max-width: 600px; margin: auto; padding: 20px; border: 1px solid #ddd; border-radius: 10px; text-align: center; font-family: Arial, sans-serif;\">"
                + "<h2 style=\"color: #333;\">비밀번호 변경</h2>"
                + "<p>아래 버튼을 클릭하여 인증을 완료하세요</p>"
                + "<a href=\"" + resetUrl + "\" style=\"display: inline-block; padding: 10px 20px; margin: 20px 0; font-size: 18px; color: #fff; background-color: #007bff; text-decoration: none; border-radius: 5px;\">"
                + "인증</a>"
                + "</div>";

        sendEmail(to, subject, content);
    }
}
