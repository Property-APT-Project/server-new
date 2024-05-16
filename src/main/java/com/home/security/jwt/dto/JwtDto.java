package com.home.security.jwt.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class JwtDto {

    private String grantType;
    private String accessToken;
    private String refreshToken;

}
