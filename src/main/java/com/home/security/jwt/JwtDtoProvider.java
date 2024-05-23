package com.home.security.jwt;

import com.home.security.jwt.dto.AccessTokenDto;
import com.home.security.jwt.dto.JwtDto;
import com.home.security.jwt.dto.RefreshTokenDto;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.Jwts.SIG;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.spec.EncodedKeySpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class JwtDtoProvider {

    private final PublicKey publicKey;
    private final PrivateKey privateKey;
    private final UserDetailsService userDetailService;

    public JwtDtoProvider(UserDetailsService userDetailsService) {
        publicKey = getPublicKey();
        privateKey = getPrivateKey();
        this.userDetailService = userDetailsService;
    }

    public JwtDto generateToken(Authentication authentication) {
        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        long now = new Date().getTime();

        Date accessTokenExpiresIn = new Date(now + 1000 * 60 * 30 * 300); //1000 * 60 * 30);
        String accessToken = Jwts.builder()
                .subject(authentication.getName())
                .claim("auth", authorities)
                .expiration(accessTokenExpiresIn)
                .signWith(privateKey, SIG.RS256)
                .compact();

        String refreshToken = Jwts.builder()
                .random(new SecureRandom())
                .subject(authentication.getName())
                .expiration(new Date(now + 1000 * 60 * 60 * 7))
                .signWith(privateKey, SIG.RS256)
                .compact();
        
        return JwtDto.builder()
                .grantType("Bearer")
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }
    
    public AccessTokenDto getAccessToken(JwtDto jwtDto) {
    	return AccessTokenDto.builder()
    			.grantType(jwtDto.getGrantType())
    			.accessToken(jwtDto.getAccessToken())
    			.build();
    }
    
    public RefreshTokenDto getRefreshToken(JwtDto jwtDto) {
    	return RefreshTokenDto.builder()
    			.grantType(jwtDto.getGrantType())
    			.refreshToken(jwtDto.getRefreshToken())
    			.build();
    }
    
    private PublicKey getPublicKey() {
        File publicKeyFile = new File("src/main/resources/public.key");
        PublicKey publicKey = null;
        try {
            byte[] publicKeyBytes = Files.readAllBytes(publicKeyFile.toPath());
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(publicKeyBytes);
            publicKey = keyFactory.generatePublic(publicKeySpec);
        } catch (IOException | InvalidKeySpecException | NoSuchAlgorithmException e) {
            log.info("an error occured obtaining key");
            log.info(e.getMessage());
        }
        return publicKey;
    }

    private PrivateKey getPrivateKey() {
        File privateKeyFile = new File("src/main/resources/private.key");
        PrivateKey privateKey = null;
        try {
            byte[] privateKeyBytes = Files.readAllBytes(privateKeyFile.toPath());
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(privateKeyBytes);
            privateKey = keyFactory.generatePrivate(privateKeySpec);
        } catch (IOException | InvalidKeySpecException | NoSuchAlgorithmException e) {
            log.info("an error occured obtaining key");
            log.info(e.getMessage());
        }
        return privateKey;
    }

    public Authentication getAuthentication(String accessToken) {
        Claims claims = parseClaims(accessToken);

        if (claims.get("auth") == null) {
            throw new RuntimeException("권한 정보가 없는 토큰입니다");
        }

        Collection<? extends GrantedAuthority> authorities =
                Arrays.stream(claims.get("auth").toString().split(","))
                        .map(SimpleGrantedAuthority::new)
                        .toList();

//        Collection<? extends GrantedAuthority> authorities = new ArrayList<>();

        System.out.println(claims);

        userDetailService.loadUserByUsername(claims.getSubject());
        UserDetails principal = new User(claims.getSubject(), "", authorities);
        return new UsernamePasswordAuthenticationToken(claims.getSubject(), "", authorities);
//        return new UsernamePasswordAuthenticationToken(principal, "", authorities);
    }
    
    public JwtDto generateJwtDtoByRefreshToken(String refreshToken) {
    	Claims claims = parseClaims(refreshToken);

        String username = claims.getSubject();
        System.out.println();
        UserDetails userDetails = userDetailService.loadUserByUsername(username);
        Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();
        
        long now = new Date().getTime();

        Date accessTokenExpiresIn = new Date(now + 1000 * 60 * 30); //1000 * 60 * 30);
        String accessToken = Jwts.builder()
                .subject(claims.getSubject())
                .claim("auth", authorities)
                .expiration(accessTokenExpiresIn)
                .signWith(privateKey, SIG.RS256)
                .compact();

        return JwtDto.builder()
                .grantType("Bearer")
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser()
                    .verifyWith(publicKey)
                    .build()
                    .parse(token);
            return true;
        } catch (SecurityException | MalformedJwtException e) {
            log.info("Invalid JWT Token", e);
        } catch (ExpiredJwtException e) {
            log.info("Expired JWT Token", e);
        } catch (UnsupportedJwtException e) {
            log.info("Unsupported JWT Token", e);
        } catch (IllegalArgumentException e) {
            log.info("JWT claims string is empty.", e);
        }
        return false;
    }
    
    public boolean validateRefreshToken(String token) {
    	try {
    		Jwts.parser()
    		.verifyWith(publicKey)
    		.build()
    		.parse(token);
    		return true;
    	} catch (SecurityException | MalformedJwtException e) {
    		log.info("Invalid JWT Token", e);
    	} catch (ExpiredJwtException e) {
    		log.info("Expired JWT Token", e);
    	} catch (UnsupportedJwtException e) {
    		log.info("Unsupported JWT Token", e);
    	} catch (IllegalArgumentException e) {
    		log.info("JWT claims string is empty.", e);
    	}
    	return false;
    }

    public Claims parseClaims(String accessToken) {
        try {
            return (Claims) Jwts.parser()
                    .verifyWith(publicKey)
                    .build()
                    .parse(accessToken)
                    .getPayload();
        } catch (ExpiredJwtException e) {
            return e.getClaims();
        }
    }

    public Date getExpiration(String token) {
        Claims claims = parseClaims(token);
        return claims.getExpiration();
    }

    public String getName(String token) {
        Claims claims = parseClaims(token);
        return claims.getSubject();
    }
}
