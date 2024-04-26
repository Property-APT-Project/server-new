package com.home.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.home.dto.HouseDealDto;
import com.home.mapper.HouseDealMapper;

import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class HouseDealServiceImpl implements HouseDealService {
	
    private final HouseDealMapper mapper;
    

	@Override
	public List<HouseDealDto> findAllByAptCode(String aptName) throws Exception {
		return mapper.findAllByAptCode(aptName);
	}

	@Override
	public List<HouseDealDto> findAllByAptCode(String aptName, int year) throws Exception {
		return mapper.findAllByAptCodeYear(aptName, year);
	}

	@Override
	public List<HouseDealDto> findAllByAptCode(String aptName, int year, int month) throws Exception {
		return mapper.findAllByAptCodeYearMonth(aptName, year, month);
	}
	
}
