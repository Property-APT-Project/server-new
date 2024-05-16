package com.home.security.jwt;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class JwtResult {

    private String grantType;
    private String accessToken;
    private String refreshToken;

}
