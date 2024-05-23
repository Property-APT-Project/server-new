package com.home.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PasswordDto {

    String email;
    String curPassword;
    String newPassword;
    String confirmPassword;
}
