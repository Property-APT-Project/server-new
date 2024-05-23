package com.home.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.home.dto.HouseDetailDto;
import com.home.mapper.HouseDetailMapper;

import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class HouseDetailServiceImpl implements HouseDetailService {
	
	private final HouseDetailMapper mapper;
	

	@Override
	public List<HouseDetailDto> findDetailInfoByApt(String aptName, String dongCode) throws Exception {

		return mapper.findDetailInfoByApt(aptName, dongCode);
	}

}
