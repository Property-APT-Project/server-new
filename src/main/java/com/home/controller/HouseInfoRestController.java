package com.home.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.home.dto.HouseInfoDto;
import com.home.service.HouseInfoService;
import com.home.service.HouseSimpleService;


@RestController
@RequestMapping("/api/v1/house-info")
public class HouseInfoRestController {
	private final HouseInfoService infoService;
	private final HouseSimpleService simpleService;

	public HouseInfoRestController(HouseInfoService infoService, HouseSimpleService simpleService) {
		this.infoService = infoService;
		this.simpleService = simpleService;
	}
	
	@GetMapping("/dong-code/{code}")
	public ResponseEntity<?> getHouseInfoByDongCode(@PathVariable("code") String code){
		try {
			List<HouseInfoDto> houseInfoList = infoService.findAllbyDongCode(code);
			return ResponseEntity.status(HttpStatus.OK).body(houseInfoList);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(e);
		}
	}
	
	@GetMapping("/keyword/{name}")
	public ResponseEntity<?> getHouseInfoListByKeyword(@PathVariable("name") String name){
		try {
			List<HouseInfoDto> houseInfoList = infoService.findAllbyKeyword(name);
			return ResponseEntity.status(HttpStatus.OK).body(houseInfoList);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(e);
		}
	}
	
	
	@GetMapping("/location-arrange")
	public ResponseEntity<?> getHouseInfoByRange(@Params()){
		try {
			List<HouseInfoDto> houseInfoList = simpleService
			return ResponseEntity.status(HttpStatus.OK).body(houseInfoList);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(e);
		}
	}
	
	
}
