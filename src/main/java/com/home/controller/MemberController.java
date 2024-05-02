package com.home.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/members")
public class MemberController {

    @Value("${spring.application.name}")
    String serviceName;

    @GetMapping("/health")
    public ResponseEntity<?> checkHealth(HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.OK).body(
                String.format("%d port %s Connected", request.getServerPort(), serviceName)
        );
    }

    public static void main(String[] args) {


    }
}
