package com.home.service;

import java.util.List;

import com.home.dto.SearchDto;


public interface SearchService {
	// keyword 검색하면 DB 업데이트
	void updateKeywordCount(String keyword) throws Exception;
	
	// 정렬한 top rank n까지 리스트 주기
	
	List<SearchDto> getTopSearchRankList(int rank) throws Exception;

}
