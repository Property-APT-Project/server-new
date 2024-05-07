package com.home.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.home.dto.SearchDto;


@Mapper
public interface SearchMapper {
	void insertKeyword(String keyword) throws Exception;
	SearchDto isExistKeyword(String keyword) throws Exception;
	void updateKeywordCnt(String keyword) throws Exception;
	List<SearchDto> getAll() throws Exception;
	
}
