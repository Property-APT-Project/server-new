package com.home.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.home.dto.MemberDto;
import com.home.service.MemberService;
import com.home.util.jwt.JwtDto;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberController {

	private final MemberService memberService;
	
    @Value("${spring.application.name}")
    String serviceName;

    @GetMapping("/health")
    public ResponseEntity<?> checkHealth(HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.OK).body(
                String.format("%d port %s Connected", request.getServerPort(), serviceName)
        );
    }
    
    @PostMapping("/join")
    public ResponseEntity<?> join(@RequestBody MemberDto memberDto) {
    	try {
    		Long id = memberService.join(memberDto);
    		return ResponseEntity.status(HttpStatus.OK).body("성공적으로 회원가입되었습니다.");
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(e.getMessage());
		}
    }
    
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody MemberDto memberDto) {
    	String username = memberDto.getEmail();
    	String password = memberDto.getPassword();
    	JwtDto jwtDto = memberService.login(username, password);
		return ResponseEntity.status(HttpStatus.OK).body(jwtDto);
    }
    
    @PutMapping("/profile")
    public ResponseEntity<?> modify() {
		return null;
    }
    
    @DeleteMapping("/leave")
    public ResponseEntity<?> leave() {
		return null;
    }
    
    @GetMapping("/logout")
    public ResponseEntity<?> logout() {
		return null;
    }
    
    public static void main(String[] args) {


    }
}
