package com.home.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.home.dto.DongCodeDto;


@Mapper
public interface DongCodeMapper {
	
	List<DongCodeDto> findAllSido() throws Exception;
	List<DongCodeDto> findGugunBySido(DongCodeDto sido) throws Exception;
	List<DongCodeDto> findDongByGugun(DongCodeDto gugun) throws Exception;

}
