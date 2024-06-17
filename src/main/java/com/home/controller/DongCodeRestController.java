package com.home.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.home.dto.DongCodeDto;
import com.home.service.DongCodeService;

import io.swagger.v3.oas.annotations.parameters.RequestBody;

@RestController
@RequestMapping("/api/v1/dong-code")
public class DongCodeRestController {
	private final DongCodeService service;

	public DongCodeRestController(DongCodeService service) {
		this.service = service;
	}
	
	@GetMapping("/dong-code/sido")
	public ResponseEntity<?> getAllSidoList(){
		try {
			List<DongCodeDto> sidoList = service.findAllSido();
			return ResponseEntity.status(HttpStatus.OK).body(sidoList);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(e);
		}
	}
	
	
	@GetMapping("/dong-code/gugun/{sido}")
	public ResponseEntity<?> getAllGugunList(@PathVariable("sido") String sido){
		try {
			List<DongCodeDto> gugunList= service.findGugunBySido(sido);
			return ResponseEntity.status(HttpStatus.OK).body(gugunList);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(e);
		}
	}
	
	
	@GetMapping("/dong-code/dong/{gugun}")
	public ResponseEntity<?> getAllDongList(@PathVariable("gugun") String gugun){
		try {
			System.out.println(gugun);
			List<DongCodeDto> dongList= service.findDongByGugun(gugun);
			return ResponseEntity.status(HttpStatus.OK).body(dongList);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(e);
		}
	}

}
