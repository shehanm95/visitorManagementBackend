package com.tacniz.visitormanagement.service.impl;

import com.tacniz.visitormanagement.dto.LoginRequest;
import com.tacniz.visitormanagement.dto.RefreshTokenRequest;
import com.tacniz.visitormanagement.dto.RegisterRequest;
import com.tacniz.visitormanagement.dto.TokenPair;
import com.tacniz.visitormanagement.model.UserEntity;
import com.tacniz.visitormanagement.repo.UserEntityRepository;
import com.tacniz.visitormanagement.service.EmailService;
import com.tacniz.visitormanagement.service.JwtService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AuthServiceImplTest {

    @InjectMocks
    private AuthServiceImpl authService;

    @Mock private UserEntityRepository userRepository;
    @Mock private PasswordEncoder passwordEncoder;
    @Mock private AuthenticationManager authenticationManager;
    @Mock private JwtService jwtService;
    @Mock private UserDetailsService userDetailsService;
    @Mock private EmailService emailService;

    @Mock private Authentication authentication;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void registerUser_success() {
        RegisterRequest request = RegisterRequest.builder()
                .firstName("a")
                .lastName("b")
                .email("test@test.com")
                .phoneNumber("1234567890")
                .password("password123")
                .imagePath(null)
                .build();

        when(userRepository.existsByEmail(request.getEmail())).thenReturn(false);
        when(passwordEncoder.encode(request.getPassword())).thenReturn("encodedPass");
        when(userRepository.save(any(UserEntity.class))).thenAnswer(inv -> inv.getArgument(0));

        when(authenticationManager.authenticate(any())).thenReturn(authentication);
        when(jwtService.generateTokenPair(any())).thenReturn(new TokenPair("access", "refresh"));

        TokenPair tokenPair = authService.registerUser(request);

        assertNotNull(tokenPair);
        assertEquals("access", tokenPair.getAccessToken());
        assertEquals("refresh", tokenPair.getRefreshToken());
        verify(userRepository).save(any(UserEntity.class));
        verify(emailService).sendFourDigitAuthenticationEmail(request.getEmail());
    }

    @Test
    void registerUser_existingEmail_throwsException() {
        RegisterRequest request = RegisterRequest.builder()
                .firstName("a")
                .lastName("b")
                .email("test@test.com")
                .phoneNumber("1234567890")
                .password("password123")
                .imagePath(null)
                .build();

        when(userRepository.existsByEmail(request.getEmail())).thenReturn(true);

        Exception ex = assertThrows(IllegalArgumentException.class, () -> {
            authService.registerUser(request);
        });

        assertEquals("Username is already in use", ex.getMessage());
    }

    @Test
    void login_success() {
        LoginRequest loginRequest = new LoginRequest("test@test.com", "password");

        when(authenticationManager.authenticate(any())).thenReturn(authentication);
        when(jwtService.generateTokenPair(any())).thenReturn(new TokenPair("access", "refresh"));

        TokenPair tokenPair = authService.login(loginRequest);

        assertNotNull(tokenPair);
        assertEquals("access", tokenPair.getAccessToken());
        assertEquals("refresh", tokenPair.getRefreshToken());
    }

    @Test
    void refreshToken_success() {
        RefreshTokenRequest refreshTokenRequest = new RefreshTokenRequest("valid-refresh-token");

        String username = "test@test.com";
        UserDetails userDetails = new User(username, "password", Collections.emptyList());

        when(jwtService.isRefreshToken("valid-refresh-token")).thenReturn(true);
        when(jwtService.extractUsernameFromToken("valid-refresh-token")).thenReturn(username);
        when(userDetailsService.loadUserByUsername(username)).thenReturn(userDetails);
        when(jwtService.generateAccessToken(any())).thenReturn("new-access-token");

        TokenPair result = authService.refreshToken(refreshTokenRequest);

        assertNotNull(result);
        assertEquals("new-access-token", result.getAccessToken());
        assertEquals("valid-refresh-token", result.getRefreshToken());
    }

    @Test
    void refreshToken_invalidToken_throwsException() {
        RefreshTokenRequest refreshTokenRequest = new RefreshTokenRequest("invalid-token");

        when(jwtService.isRefreshToken("invalid-token")).thenReturn(false);

        Exception ex = assertThrows(IllegalArgumentException.class, () -> {
            authService.refreshToken(refreshTokenRequest);
        });

        assertEquals("Invalid refresh token", ex.getMessage());
    }

    @Test
    void refreshToken_userNotFound_throwsException() {
        RefreshTokenRequest refreshTokenRequest = new RefreshTokenRequest("valid-refresh-token");

        when(jwtService.isRefreshToken("valid-refresh-token")).thenReturn(true);
        when(jwtService.extractUsernameFromToken("valid-refresh-token")).thenReturn("user@example.com");
        when(userDetailsService.loadUserByUsername("user@example.com")).thenReturn(null);

        Exception ex = assertThrows(IllegalArgumentException.class, () -> {
            authService.refreshToken(refreshTokenRequest);
        });

        assertEquals("User not found", ex.getMessage());
    }
}
