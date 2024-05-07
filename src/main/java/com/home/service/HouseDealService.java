package com.home.service;

import java.util.List;

import com.home.dto.HouseDealDto;


public interface HouseDealService {
	List<HouseDealDto> findAllByAptCode(String aptName) throws Exception;
	List<HouseDealDto> findAllByAptCode(String aptName, int year) throws Exception;
	List<HouseDealDto> findAllByAptCode(String aptName, int year, int month) throws Exception;
}
