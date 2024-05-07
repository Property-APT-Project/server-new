package com.home.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.home.dto.HouseInfoDto;

@Mapper
public interface HouseInfoMapper {
	List<HouseInfoDto> findAllbyDongCode(String code) throws Exception;
	List<HouseInfoDto> findAllbyKeyword(String name) throws Exception;

}
