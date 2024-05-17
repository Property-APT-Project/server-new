package com.home.service;

import java.util.List;

import com.home.dto.HouseSimpleDto;

public interface HouseSimpleService {
	List<HouseSimpleDto> findAptListByLngLatRange(String sLat, String eLat, String sLng, String eLng);
	

}
