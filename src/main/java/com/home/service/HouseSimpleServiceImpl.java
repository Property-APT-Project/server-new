package com.home.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.home.dto.HouseSimpleDto;
import com.home.mapper.HouseSimpleMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class HouseSimpleServiceImpl implements HouseSimpleService{
	private final HouseSimpleMapper mapper;

	@Override
	public List<HouseSimpleDto> findAptListByLngLatRange(String sLat, String eLat, String sLng, String eLng) {
		// TODO Auto-generated method stub
		return mapper.findAptListByLngLatRange(sLat, eLat, sLng, eLng);
	}

	
}
