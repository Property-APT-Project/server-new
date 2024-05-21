package com.home.controller;

import com.home.dto.MemberDto;
import com.home.dto.PasswordDto;
import com.home.dto.PasswordResetTokenDto;
import com.home.mapper.PasswordResetTokenMapper;
import com.home.service.EmailService;
import com.home.service.MemberService;
import com.home.service.PasswordService;
import jakarta.mail.MessagingException;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/reset-password")
public class PasswordResetController {

    private final MemberService memberService;
    private final EmailService emailService;
    private final PasswordResetTokenMapper tokenMapper;
    private final PasswordEncoder passwordEncoder;
    private final PasswordService passwordService;

    @PostMapping("/request")
    public ResponseEntity<String> requestPasswordReset(@RequestParam String email) {

        MemberDto memberDto = memberService.findByEmail(email);
        if (memberDto == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("해당 이메일이 존재하지 않습니다.");
        }
        String token = UUID.randomUUID().toString();
        PasswordResetTokenDto resetToken = new PasswordResetTokenDto();
        resetToken.setToken(token);
        resetToken.setEmail(email);
        resetToken.setExpiryDate(LocalDateTime.now().plusMinutes(5));
        tokenMapper.insertToken(resetToken);

//        String resetUrl = "http://localhost:8080/where-is-my-home/reset-password?token=" + token;
//        emailService.sendEmail(email, "Password Reset Request", "Click the link to reset your password: " + resetUrl);
        try {
            emailService.sendPasswordResetEmail(email, token);
        } catch (MessagingException e) {
            return ResponseEntity.status(500).body("Failed to send email");
        }
        return ResponseEntity.ok("Password reset email sent");
    }

    @PostMapping("/reset")
    public ResponseEntity<String> resetPassword(@RequestParam String token, @RequestBody
            PasswordDto passwordDto) {
        PasswordResetTokenDto resetToken = tokenMapper.findByToken(token);

        String newPassword = passwordDto.getNewPassword();
        String confirmPassword = passwordDto.getConfirmPassword();
        try {

            passwordService.validatePassword(newPassword);
            passwordService.validatePasswordSame(newPassword, confirmPassword);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
//        if (!passwordDto.getNewPassword().equals(passwordDto.getConfirmPassword())) {
//            return ResponseEntity.badRequest().body("비밀번호가 일치하지 않습니다.");
//        }
        if (resetToken == null || resetToken.getExpiryDate().isBefore(LocalDateTime.now())) {
            return ResponseEntity.badRequest().body("토큰이 유효하지 않습니다.");
        }

        // 비밀번호 업데이트 로직 추가 (예: 유저 레포지토리에서 유저를 찾아 비밀번호 업데이트)
        MemberDto memberDto = memberService.findByEmail(resetToken.getEmail());
        memberDto.setPassword(passwordEncoder.encode(newPassword));

        memberService.update(memberDto.getId(), memberDto);
        tokenMapper.deleteByToken(token);

        return ResponseEntity.ok("Password reset successfully");
    }

    @GetMapping("/validate-token")
    public ResponseEntity<String> validateToken(@RequestParam String token) {
        PasswordResetTokenDto resetToken = tokenMapper.findByToken(token);

        System.out.println(resetToken);
        if (resetToken == null || resetToken.getExpiryDate().isBefore(LocalDateTime.now())) {
            return ResponseEntity.badRequest().body("Invalid or expired token");
        }

        return ResponseEntity.ok("Token is valid");
    }
}
