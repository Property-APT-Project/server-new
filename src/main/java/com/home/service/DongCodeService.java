package com.home.service;

import java.util.List;

import com.home.dto.DongCodeDto;


public interface DongCodeService {
	List<DongCodeDto> findAllSido() throws Exception;
	List<DongCodeDto> findGugunBySido(String sido) throws Exception;
	List<DongCodeDto> findDongByGugun(String gugun) throws Exception;
}
