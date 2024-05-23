package com.home.mapper;

import com.home.dto.PasswordResetTokenDto;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PasswordResetTokenMapper {
    void insertToken(PasswordResetTokenDto token);
    PasswordResetTokenDto findByToken(String token);
    void deleteByToken(String token);
}
