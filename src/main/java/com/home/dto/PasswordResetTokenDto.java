package com.home.dto;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class PasswordResetTokenDto {
    private Long id;
    private String token;
    private String email;
    private LocalDateTime expiryDate;
}
