package com.home.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.home.dto.HouseDetailDto;

@Mapper
public interface HouseDetailMapper {
	List<HouseDetailDto> findDetailInfoByApt(@Param("aptName") String aptName, @Param("dongCode") String dongCode) throws Exception;
	
}
