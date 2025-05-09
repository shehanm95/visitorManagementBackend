package com.tacniz.visitormanagement.service;

import com.tacniz.visitormanagement.dto.LoginRequest;
import com.tacniz.visitormanagement.dto.RefreshTokenRequest;
import com.tacniz.visitormanagement.dto.RegisterRequest;
import com.tacniz.visitormanagement.dto.TokenPair;

public interface AuthService {

    TokenPair registerUser(RegisterRequest registerRequest);

    TokenPair login(LoginRequest loginRequest);

    TokenPair refreshToken(RefreshTokenRequest request);
}