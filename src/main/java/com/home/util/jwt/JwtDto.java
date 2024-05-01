package com.home.util.jwt;

import io.jsonwebtoken.Jwts.SIG;
import io.jsonwebtoken.security.SignatureAlgorithm;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.EncodedKeySpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import lombok.Builder;

@Builder
public class JwtDto {

    private String grantType;
    private String accessToken;
    private String refreshToken;

    public static void main(String[] args) {
        File publicKeyFile = new File("public.key");
        try {
            byte[] publicKeyBytes = Files.readAllBytes(publicKeyFile.toPath());
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(publicKeyBytes);
            PublicKey publicKey = keyFactory.generatePublic(publicKeySpec);
            System.out.println(publicKey);
        } catch (IOException | InvalidKeySpecException | NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        SignatureAlgorithm alg = SIG.RS256;
    }
}
