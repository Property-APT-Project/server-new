package com.home.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.home.dto.HouseSimpleDto;

@Mapper
public interface HouseSimpleMapper {
	List<HouseSimpleDto> findAptListByLngLatRange(@Param("sLat") String sLat, @Param("eLat") String eLat, @Param("sLng") String sLng, @Param("eLng") String eLng);
	List<HouseSimpleDto> findAptListByDongCode(@Param("dongCode") String dongCode);
}
