package com.tacniz.visitormanagement.service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

import java.util.Date;

import static org.springframework.cache.interceptor.SimpleKeyGenerator.generateKey;

@Service
public class JwtService {
    private final SecretKey secretKey;

    public JwtService() {
        try {
            SecretKey k = KeyGenerator.getInstance( "HmacSHA256").generateKey();
            secretKey = Keys.hmacShaKeyFor(k.getEncoded());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }public String getJWTToken() {
        return Jwts.builder()
                .subject("zincat")
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis()+1000*60*15))
                .signWith(secretKey)
                .compact();
    }

    public String getUsername(String token) {
        try{
            return Jwts.parser()
                    .verifyWith(secretKey)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload()
                    .getSubject();
        }catch (Exception e){
            throw new RuntimeException("invalid token : shehan ");
        }
    }
}
