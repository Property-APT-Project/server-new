package com.home.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.home.dto.DongCodeDto;


@Mapper
public interface DongCodeMapper {
	
	List<DongCodeDto> findAllSido() throws Exception;
	List<DongCodeDto> findGugunBySido(@Param("dongCode") String sido) throws Exception;
	List<DongCodeDto> findDongByGugun(@Param("dongCode") String gugun) throws Exception;

}
