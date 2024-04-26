package com.home.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.home.dto.DongCodeDto;
import com.home.mapper.DongCodeMapper;

import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class DongCodeServiceImpl implements DongCodeService {
	private final DongCodeMapper mapper;

	@Override
	public List<DongCodeDto> findAllSido() throws Exception {
		return mapper.findAllSido();
	}

	@Override
	public List<DongCodeDto> findGugunBySido(DongCodeDto sido) throws Exception {
		return mapper.findGugunBySido(sido);
	}

	@Override
	public List<DongCodeDto> findDongByGugun(DongCodeDto gugun) throws Exception {
		return mapper.findDongByGugun(gugun);
	}

}
