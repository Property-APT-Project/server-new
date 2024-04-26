package com.home.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.home.dto.HouseInfoDto;
import com.home.mapper.HouseInfoMapper;

import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class HouseInfoServiceImpl implements HouseInfoService {
    
    private final HouseInfoMapper mapper;
    
	

	@Override
	public List<HouseInfoDto> findAllbyDongCode(String code) throws Exception {
		return mapper.findAllbyDongCode(code);
	}

	@Override
	public List<HouseInfoDto> findAllbyKeyword(String name) throws Exception {
		return mapper.findAllbyKeyword(name);
	}

}
