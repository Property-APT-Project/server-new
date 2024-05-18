package com.home.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.home.dto.HouseSaleDto;
import com.home.mapper.HouseSaleMapper;

import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class HouseSaleServiceImpl implements HouseSaleService {
	private final HouseSaleMapper mapper;

	@Override
	public List<HouseSaleDto> findSaleListByAptName(String aptName, String dongCode) throws Exception {
		return mapper.findSaleListByAptName(aptName, dongCode);
	}

	@Override
	public List<HouseSaleDto> findSaleListByLocation(String sLat, String eLat, String sLng, String eLng)
			throws Exception {
		return mapper.findSaleListByLocation(sLat, eLat, sLng, eLng);
	}

}
