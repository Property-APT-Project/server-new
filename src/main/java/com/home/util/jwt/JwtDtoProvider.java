package com.home.util.jwt;

import com.home.mapper.MemberMapper;
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
import java.security.spec.EncodedKeySpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class JwtDtoProvider {

    private final PublicKey publicKey;
    private final PrivateKey privateKey;
    //    private final UserDetailsService userDetailsService;
    private final MemberMapper memberMapper;

    @Autowired
    public JwtDtoProvider(MemberMapper memberMapper) {
        publicKey = getPublicKey();
        privateKey = getPrivateKey();
        this.memberMapper = memberMapper;
    }

    public JwtDto generateToken(Authentication authentication) {
        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        long now = new Date().getTime();

        Date accessTokenExpiresIn = new Date(now + 1000 * 60 * 60 * 7); //1000 * 60 * 30);
        String accessToken = Jwts.builder()
                .subject(authentication.getName())
                .claim("auth", authorities)
                .expiration(accessTokenExpiresIn)
                .signWith(privateKey, SIG.RS256)
                .compact();

        String refreshToken = Jwts.builder()
                .expiration(new Date(now + 1000 * 60 * 60 * 7))
                .signWith(privateKey, SIG.RS256)
                .compact();

        return JwtDto.builder()
                .grantType("Bearer")
                .accessToken(accessToken)
                .refreshToken(refreshToken)
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

//        Collection<? extends GrantedAuthority> authorities =
//                Arrays.stream(claims.get("auth").toString().split(","))
//                        .map(SimpleGrantedAuthority::new)
//                        .toList();

        Collection<? extends GrantedAuthority> authorities = new ArrayList<>();

        System.out.println(claims);

//        MemberDto memberDto = (MemberDto) userDetailsService.loadUserByUsername(claims.getSubject());
        UserDetails principal = memberMapper.findByEmail(claims.getSubject());
//                .map(this::createUserDetails)
//                .orElseThrow(() -> new UsernameNotFoundException("해당 회원을 찾을 수 없습니다."));
//        MemberDetails principal = new MemberDetails(memberDto);
//        UserDetails principal = new User(claims.getSubject(), "", authorities);

        return new UsernamePasswordAuthenticationToken(principal, "", authorities);
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

    private Claims parseClaims(String accessToken) {
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
}
