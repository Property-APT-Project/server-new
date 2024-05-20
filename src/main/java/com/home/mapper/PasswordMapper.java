package com.home.mapper;

import com.home.dto.PasswordDto;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PasswordMapper {

    String findByEmail(String email);

    void update(PasswordDto passwordDto);
}
