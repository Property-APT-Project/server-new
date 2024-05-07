package com.home.service;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

import org.springframework.stereotype.Service;

import com.home.dto.SearchDto;
import com.home.mapper.SearchMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SearchServiceImpl implements SearchService {
	private final SearchMapper mapper;
	

	@Override
	public void updateKeywordCount(String keyword) throws Exception {
		if(mapper.isExistKeyword(keyword) != null) {
			mapper.updateKeywordCnt(keyword);
		}
		else {
			mapper.insertKeyword(keyword);
		}

	}

	@Override
	public List<SearchDto> getTopSearchRankList(int rank) throws Exception {
		List<SearchDto> list = mapper.getAll();
		
		PriorityQueue<SearchDto> pq = new PriorityQueue<>();
		
		for(SearchDto s: list)
			pq.offer(s);
		
		List<SearchDto> ret = new ArrayList<>();
		
		if(pq.size() < rank)
			rank = pq.size();
		
		for(int i = 0; i < rank; i++)
			ret.add(pq.poll());
		
		
		return ret;
	}


}
