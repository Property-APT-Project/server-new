package com.home.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.home.dto.InterestDto;
import com.home.service.InterestService;

import org.springframework.web.bind.annotation.RequestBody;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/interest")
public class InterestRestController {
	
	private final InterestService interestService;
	
	@GetMapping("/list/complex")
	public ResponseEntity<?> findComplexList(){
		try {
			List<InterestDto> complexList = interestService.findAllUserInterestComplex();
			return ResponseEntity.status(HttpStatus.OK).body(complexList);
		} catch (Exception e) {
			log.info(e.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("interest 등록 실");
			
		}
		
	}
	
	@GetMapping("/list/sale")
	public ResponseEntity<?> findSaleList(){
		try {
			List<InterestDto> saleList = interestService.findAllUserInterestSale();
			return ResponseEntity.status(HttpStatus.OK).body(saleList);
		} catch (Exception e) {
			log.info(e.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("interest 등록 실");
			
		}
		
	}
	
	
	@PostMapping("/post")
	public ResponseEntity<?> registInterest(@RequestBody InterestDto interestDto){
		
		System.out.println("컨트롤러에서 :" + interestDto);
		try {
			interestService.insertInterest(interestDto);
			return ResponseEntity.status(HttpStatus.OK).body("interest 등록성공 ");
		} catch (Exception e) {
			log.info(e.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("interest 등록 실패 ");
			
		}
		
	}
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<?> delete(@PathVariable("id") String interestId){
		try {
			interestService.deleteInterest(interestId);
			return ResponseEntity.status(HttpStatus.OK).body("interest 삭제 성공 ");
		} catch (Exception e) {
			log.info(e.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("interest 삭제 실패 ");
			
		}
		
	}
	
	

}
