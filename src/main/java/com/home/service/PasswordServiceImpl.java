package com.home.service;

import com.home.dto.PasswordDto;
import com.home.mapper.PasswordMapper;
import java.util.regex.Pattern;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PasswordServiceImpl implements PasswordService{

    private final PasswordEncoder passwordEncoder;
    private final PasswordMapper passwordMapper;

    @Override
    public void changePassword(PasswordDto passwordDto) throws IllegalArgumentException {
        String email = passwordDto.getEmail();
        String curPassword = passwordDto.getCurPassword();
        String newPassword = passwordDto.getNewPassword();
        String confirmPassword = passwordDto.getConfirmPassword();

        validatePassword(newPassword);
        validatePasswordSame(newPassword, confirmPassword);

        String originPassword = passwordMapper.findByEmail(email);
        validateCurPassword(originPassword, curPassword);

        passwordDto.setNewPassword(passwordEncoder.encode(newPassword));
        passwordMapper.update(passwordDto);
    }

    private void validateCurPassword(String originPassword, String curPassword) {
        if (!passwordEncoder.matches(curPassword, originPassword)) {
            throw new IllegalArgumentException("비밀번호가 틀렸습니다.");
        }
    }

    private void validatePasswordSame(String password, String confirmPassword) {
        if (!password.equals(confirmPassword)) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }
    }

    private void validatePassword(String password) {
        if (patternMatches(password,
                "^(?=.*[A-Za-z])(?=.*\\d)(?=(.*[!@#$%^&*(),.?\":{}|<>]){2,}).{8,}$")) {
            throw new IllegalArgumentException("비밀번호가 유효하지 않습니다.");
        }
    }

    private static boolean patternMatches(String pattern, String regexPattern) throws IllegalArgumentException{
        if (pattern == null) {
            throw new IllegalArgumentException("항목이 존재하지 않습니다.");
        }
        return !Pattern.compile(regexPattern)
                .matcher(pattern)
                .matches();
    }
}
