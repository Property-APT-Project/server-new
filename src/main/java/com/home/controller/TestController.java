package com.home.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/v1/test")
public class TestController {
	
	@GetMapping("/main")
	public ResponseEntity<String> status(HttpServletRequest request){
		return ResponseEntity.status(HttpStatus.OK).body(
				String.format("%s %d port Connected!", "book main",  request.getServerPort()));
	}
}
