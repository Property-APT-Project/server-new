package com.home.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.home.dto.HouseDealDto;

@Mapper
public interface HouseDealMapper {
	List<HouseDealDto> findAllByAptCode(@Param("aptName") String aptName) throws Exception;
	List<HouseDealDto> findAllByAptCodeYear(@Param("aptName") String aptName, @Param("year") int year) throws Exception;
	List<HouseDealDto> findAllByAptCodeYearMonth(@Param("aptName") String aptName, @Param("year") int year, @Param("month") int month) throws Exception;
}
