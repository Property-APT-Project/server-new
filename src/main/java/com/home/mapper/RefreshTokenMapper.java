package com.home.mapper;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface RefreshTokenMapper {
	public String findByToken(String token);
}
