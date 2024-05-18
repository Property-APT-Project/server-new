package com.home.service;

import java.util.List;

import com.home.dto.HouseDetailDto;


public interface HouseDetailService {
	List<HouseDetailDto> findDetailInfoByApt(String aptName, String dongCode) throws Exception;
}
