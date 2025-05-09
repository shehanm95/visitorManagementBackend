package com.tacniz.visitormanagement.exception;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
@Slf4j
public class JwtExceptionHandler {

    // Handle expired JWTs
    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<?> handleExpiredJwtException(ExpiredJwtException ex) {
        log.warn("Expired JWT token: {}", ex.getMessage());

        Map<String, Object> response = new HashMap<>();
        response.put("error", "TokenExpired");
        response.put("message", "JWT token has expired");
        response.put("expiredAt", ex.getClaims().getExpiration());
        response.put("timestamp", ZonedDateTime.now());

        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED); // 401
    }

    // Handle any other JWT-related errors
    @ExceptionHandler(JwtException.class)
    public ResponseEntity<?> handleJwtException(JwtException ex) {
        log.error("Invalid JWT token: {}", ex.getMessage());

        Map<String, Object> response = new HashMap<>();
        response.put("error", "InvalidToken");
        response.put("message", "JWT token is invalid or malformed");
        response.put("timestamp", ZonedDateTime.now());

        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED); // 401
    }

//    // Optionally handle other generic exceptions
//    @ExceptionHandler(Exception.class)
//    public ResponseEntity<?> handleGenericException(Exception ex) {
//        log.error("Unexpected error: {}", ex.getMessage());
//
//        Map<String, Object> response = new HashMap<>();
//        response.put("error", "InternalServerError");
//        response.put("message", "An unexpected error occurred");
//        response.put("timestamp", ZonedDateTime.now());
//
//        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
//    }
}

