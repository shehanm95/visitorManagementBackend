package com.tacniz.visitormanagement.service;

import com.tacniz.visitormanagement.dto.TokenPair;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;

public interface JwtService {

    TokenPair generateTokenPair(Authentication authentication);

    String generateAccessToken(Authentication authentication);

    String generateRefreshToken(Authentication authentication);

    boolean validateTokenForUser(String token, UserDetails userDetails);

    boolean isValidToken(String token);

    String extractUsernameFromToken(String token);

    boolean isRefreshToken(String token);
}