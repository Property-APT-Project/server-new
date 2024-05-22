package com.home.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.home.dto.HouseSaleDto;

@Mapper
public interface HouseSaleMapper {
	List<HouseSaleDto> findSaleListByAptName(@Param("aptName") String aptName, @Param("dongCode") String dongCode) throws Exception;
	List<HouseSaleDto> findSaleListByLocation(@Param("sLat") String sLat, @Param("eLat") String eLat, @Param("sLng") String sLng, @Param("eLng") String eLng) throws Exception;
	List<HouseSaleDto> findSaleListByDongCode(@Param("dongCode") String dongCode) throws Exception;
	List<HouseSaleDto> findSaleListByKeyword(@Param("aptName") String aptName) throws Exception;
	List<HouseSaleDto> findSaleListById(@Param("id") String id) throws Exception;

}
