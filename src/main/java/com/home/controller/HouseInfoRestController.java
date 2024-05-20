package com.home.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.home.dto.DongCodeDto;
import com.home.dto.HouseDetailDto;
import com.home.dto.HouseInfoDto;
import com.home.dto.HouseSaleDto;
import com.home.dto.HouseSimpleDto;
import com.home.service.HouseDetailService;
import com.home.service.HouseInfoService;
import com.home.service.HouseSaleService;
import com.home.service.HouseSimpleService;


@RestController
@RequestMapping("/api/v1/house-info")
public class HouseInfoRestController {
	private final HouseInfoService infoService;
	private final HouseSimpleService simpleService;
	private final HouseDetailService detailService;
	private final HouseSaleService saleService;
	

	public HouseInfoRestController(HouseInfoService infoService, HouseSimpleService simpleService, HouseDetailService detailService, HouseSaleService saleService) {
		this.infoService = infoService;
		this.simpleService = simpleService;
		this.detailService = detailService;
		this.saleService = saleService;
	}
	
//	@GetMapping("/complexes/dong-code/{code}")
//	public ResponseEntity<?> getHouseInfoByDongCode(@PathVariable("code") String code){
//		try {
//			List<HouseInfoDto> houseInfoList = infoService.findAllbyDongCode(code);
//			return ResponseEntity.status(HttpStatus.OK).body(houseInfoList);
//		} catch (Exception e) {
//			e.printStackTrace();
//			return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(e);
//		}
//	}
	
	@GetMapping("/complexes/keyword/{name}")
	public ResponseEntity<?> getHouseInfoListByKeyword(@PathVariable("name") String name){
		try {
			List<HouseInfoDto> houseInfoList = infoService.findAllbyKeyword(name);
			return ResponseEntity.status(HttpStatus.OK).body(houseInfoList);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(e);
		}
	}
	
	
	@GetMapping("/complexes/location")
	public ResponseEntity<?> getHouseInfoByRange(@RequestParam(value="sLat") String sLat,
			@RequestParam(value="eLat") String eLat, @RequestParam(value="sLng") String sLng,
			@RequestParam(value="eLng")String eLng){
		try {
			List<HouseSimpleDto> houseInfoList = simpleService.findAptListByLngLatRange(sLat, eLat, sLng, eLng);
			return ResponseEntity.status(HttpStatus.OK).body(houseInfoList);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(e);
		}
	}
	
	@GetMapping("/complexes/dong-code/{code}")
	public ResponseEntity<?> getHouseInfoByDongCode(@PathVariable("code") String code){
		try {
			List<HouseSimpleDto> houseInfoList = simpleService.findAptListByDongCode(code);
			return ResponseEntity.status(HttpStatus.OK).body(houseInfoList);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(e);
		}
	}
	
	
	@GetMapping("/complexes/dong-group")
	public ResponseEntity<?> getHouseInfoGroupByRange(@RequestParam(value="sLat") String sLat,
			@RequestParam(value="eLat") String eLat, @RequestParam(value="sLng") String sLng,
			@RequestParam(value="eLng")String eLng){
		try {
			List<DongCodeDto> houseInfoList = simpleService.findAptListGroupByDongByLngLatRange(sLat, eLat, sLng, eLng);
			return ResponseEntity.status(HttpStatus.OK).body(houseInfoList);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(e);
		}
	}
	
	
	@GetMapping("/complexes/detail-info/{name}")
	public ResponseEntity<?> getHouseDetailInfo(@PathVariable("name") String name, @RequestParam(value="dongCode") String dongCode){
		try {
			List<HouseDetailDto> houseDetailInfoList = detailService.findDetailInfoByApt(name, dongCode);
			return ResponseEntity.status(HttpStatus.OK).body(houseDetailInfoList);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(e);
		}
	}
	
	
	
	@GetMapping("/sale-articles/complex/{name}")
	public ResponseEntity<?> getHouseSaleInfoByComplex(@PathVariable("name") String name, @RequestParam(value="dongCode") String dongCode){
		try {
			List<HouseSaleDto> houseSaleInfoList = saleService.findSaleListByAptName(name, dongCode);
			return ResponseEntity.status(HttpStatus.OK).body(houseSaleInfoList);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(e);
		}
	}
	
	@GetMapping("/sale-articles/location")
	public ResponseEntity<?> getHouseSaleInfoByLocation(@RequestParam(value="sLat") String sLat,
			@RequestParam(value="eLat") String eLat, @RequestParam(value="sLng") String sLng,
			@RequestParam(value="eLng")String eLng){
		try {
			List<HouseSaleDto> houseSaleInfoList = saleService.findSaleListByLocation(sLat, eLat, sLng, eLng);
			return ResponseEntity.status(HttpStatus.OK).body(houseSaleInfoList);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(e);
		}
	}
	
	@GetMapping("/sale-articles/dong-code/{code}")
	public ResponseEntity<?> getHouseSaleInfoByDongCode(@PathVariable("code") String code){
		try {
			List<HouseSaleDto> houseSaleInfoList = saleService.findSaleListByDongCode(code);
			return ResponseEntity.status(HttpStatus.OK).body(houseSaleInfoList);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(e);
		}
	}
	
	
}
