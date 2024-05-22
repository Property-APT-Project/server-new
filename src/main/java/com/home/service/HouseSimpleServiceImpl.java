package com.home.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Service;

import com.home.dto.DongCodeDto;
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

	@Override
	public List<DongCodeDto> findAptListGroupByDongByLngLatRange(String sLat, String eLat,
			String sLng, String eLng) {
		
		HashMap<String, List<HouseSimpleDto>> dongAptListHashMap = new HashMap<>();
		
		List<DongCodeDto> dongList = new ArrayList<>();
		List<HouseSimpleDto> aptSimpleList = mapper.findAptListByLngLatRange(sLat, eLat, sLng, eLng);
		
		for(HouseSimpleDto simpleDto : aptSimpleList) {
			if(!dongAptListHashMap.containsKey(simpleDto.getDongCode()))
				dongAptListHashMap.put(simpleDto.getDongCode(), new ArrayList<>());
			dongAptListHashMap.get(simpleDto.getDongCode()).add(simpleDto);
		}
		
		for(String aptDongCode:dongAptListHashMap.keySet()) {
			DongCodeDto dongCodeDto = new DongCodeDto();
			
			dongCodeDto.setDongCode(aptDongCode);
			dongCodeDto.setAptList(dongAptListHashMap.get(aptDongCode));
			dongCodeDto.setAptListCount(dongAptListHashMap.get(aptDongCode).size());
			
			dongList.add(dongCodeDto);
			
		}
		
		
		return dongList;
	}

	@Override
	public List<HouseSimpleDto> findAptListByDongCode(String dongCode) {
		// TODO Auto-generated method stub
		return mapper.findAptListByDongCode(dongCode);
	}

	@Override
	public List<HouseSimpleDto> findAptListByAptName(String aptName) {
		// TODO Auto-generated method stub
		return mapper.findAptListByAptName(aptName);
	}

	
}
