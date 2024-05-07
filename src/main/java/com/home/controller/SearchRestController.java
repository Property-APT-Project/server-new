package com.home.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.home.dto.SearchDto;
import com.home.service.SearchService;



@RestController
@RequestMapping("/api/v1/search")
public class SearchRestController {
	private final SearchService service;

	public SearchRestController(SearchService service) {
		this.service = service;
	}
	
	@PostMapping("/{keyword}")
	public ResponseEntity<?> updateKeywordCount(@PathVariable("keyword") String keyword){
		try {
			System.out.println(keyword);
			service.updateKeywordCount(keyword);
			return ResponseEntity.status(HttpStatus.OK).body(keyword);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(e);
		}
	}
	
	@GetMapping("/rank/{rank}")
	public ResponseEntity<?> getSearchRankList(@PathVariable("rank") int rank){
		try {
			List<SearchDto> rankKeywordList = service.getTopSearchRankList(rank);
			return ResponseEntity.status(HttpStatus.OK).body(rankKeywordList);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(e);
		}
	}
	

}
