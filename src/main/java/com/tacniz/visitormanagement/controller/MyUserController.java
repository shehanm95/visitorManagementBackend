package com.tacniz.visitormanagement.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tacniz.visitormanagement.dto.MyUserDto;
import com.tacniz.visitormanagement.model.MyUser;
import com.tacniz.visitormanagement.service.MyUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final ObjectMapper objectMapper;
    private final MyUserService myUserService;

    @GetMapping("/user/all")
    public ResponseEntity<List<MyUserDto>> getAllUsers(){
        List<MyUserDto> userDtos = myUserService.getAllUsers()
                .stream()
                .map(user -> objectMapper.convertValue(user, MyUserDto.class))
                .collect(Collectors.toList());
        return ResponseEntity.ok(userDtos);  }
}
