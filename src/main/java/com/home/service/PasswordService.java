package com.home.service;

import com.home.dto.PasswordDto;

public interface PasswordService {

    void changePassword(PasswordDto passwordDto);

    void validatePasswordSame(String password, String confirmPassword);

    void validatePassword(String password);
}
