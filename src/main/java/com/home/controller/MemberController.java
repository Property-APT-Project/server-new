package com.home.controller;

import com.home.dto.MemberDto;
import com.home.dto.ProfileDto;
import com.home.security.jwt.JwtDto;
import com.home.service.MemberService;

import jakarta.servlet.http.HttpServletRequest;
import java.security.Principal;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
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

    @GetMapping("/test")
    public ResponseEntity<?> test(HttpServletRequest request) {
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
            log.info(e.getMessage());
			return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(e.getMessage());
		}
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody MemberDto memberDto) {
    	String username = memberDto.getEmail();
    	String password = memberDto.getPassword();
        try {
            JwtDto jwtDto = memberService.login(username, password);
            return ResponseEntity.status(HttpStatus.OK).body(jwtDto);
        } catch (Exception e) {
            log.info(e.getMessage());
            return new ResponseEntity<>("로그인에 실패하였습니다.", HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/profile")
    public ResponseEntity<?> getProfile(@AuthenticationPrincipal Principal principal) {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();

        String username = (String) authentication.getPrincipal();
        MemberDto memberDto = memberService.findByEmail(username);
        System.out.println("principal " + authentication.getPrincipal());
        System.out.println("credential " + authentication.getCredentials());
        System.out.println("detail " + authentication.getDetails());
        ProfileDto profileDto = ProfileDto.builder()
                .email(memberDto.getEmail())
                .name(memberDto.getName())
                .address(memberDto.getAddress())
                .phoneNumber(memberDto.getPhoneNumber())
                .build();
        try {
            return ResponseEntity.status(HttpStatus.OK).body(profileDto);
        } catch (Exception e) {
            return new ResponseEntity<>("조회 실패하였습니다.", HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/profile")
    public ResponseEntity<?> modify(@RequestBody MemberDto memberDto)
    {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();

        String username = (String) authentication.getPrincipal();
        try {
            memberService.updateByEmail(username, memberDto);
            return ResponseEntity.status(HttpStatus.OK).body("성공적으로 업데이트 되었습니다.");
        } catch (Exception e) {
            log.info(e.getMessage());
            return new ResponseEntity<>("업데이트 실패하였습니다.", HttpStatus.BAD_REQUEST);
        }
    }
    
    @DeleteMapping("/leave")
    public ResponseEntity<?> leave() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();

        String username = (String) authentication.getPrincipal();
        try {
            memberService.deleteByEmail(username);
            return ResponseEntity.status(HttpStatus.OK).body("성공적으로 삭제 되었습니다.");
        } catch (Exception e) {
            log.info(e.getMessage());
            return new ResponseEntity<>("삭제 실패하였습니다.", HttpStatus.BAD_REQUEST);
        }
    }
    
    @GetMapping("/logout")
    public ResponseEntity<?> logout() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        MemberDto principal = (MemberDto) authentication.getPrincipal();
        return ResponseEntity.status(HttpStatus.OK).body("성공적으로 로그아웃 되었습니다.");
    }
    
    public static void main(String[] args) {


    }
}
