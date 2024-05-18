package com.home.service;

import java.util.List;


import com.home.dto.HouseSaleDto;

public interface HouseSaleService {
	List<HouseSaleDto> findSaleListByAptName(String aptName, String dongCode) throws Exception;
	List<HouseSaleDto> findSaleListByLocation(String sLat, String eLat, String sLng, String eLng) throws Exception;

}
