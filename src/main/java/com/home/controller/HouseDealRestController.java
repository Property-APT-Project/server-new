package com.home.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.home.dto.HouseDealDto;
import com.home.service.HouseDealService;


@RestController
@RequestMapping("/api/v1/house-deal")
public class HouseDealRestController {
	private final HouseDealService service;

	public HouseDealRestController(HouseDealService service) {
		this.service = service;
	}
	
	@GetMapping("/{aptName}")
	public ResponseEntity<?> getDealListByAptName(@PathVariable("aptName") String aptName){
		try {
			List<HouseDealDto> dealList= service.findAllByAptCode(aptName);
			return ResponseEntity.status(HttpStatus.OK).body(dealList);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(e);
		}
	}
	
	@GetMapping("/{aptName}/{year}")
	public ResponseEntity<?> getDealListByAptNameYear(@PathVariable("aptName") String aptName, @PathVariable("year") int year){
		try {
			List<HouseDealDto> dealList= service.findAllByAptCode(aptName, year);
			return ResponseEntity.status(HttpStatus.OK).body(dealList);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(e);
		}
	}
	
	
	@GetMapping("/{aptName}/{year}/{month}")
	public ResponseEntity<?> getDealListByAptNameYearMonth(@PathVariable("aptName") String aptName, @PathVariable("year") int year, @PathVariable("month") int month){
		try {
			List<HouseDealDto> dealList= service.findAllByAptCode(aptName, month, month);
			return ResponseEntity.status(HttpStatus.OK).body(dealList);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(e);
		}
	}

}
