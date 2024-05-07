package com.home.service;

import java.util.List;

import com.home.dto.HouseInfoDto;


public interface HouseInfoService {
	List<HouseInfoDto> findAllbyDongCode(String code) throws Exception;	
	List<HouseInfoDto> findAllbyKeyword(String name) throws Exception;
}
