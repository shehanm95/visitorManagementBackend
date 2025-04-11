package com.tacniz.visitormanagement.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tacniz.visitormanagement.dto.MyUserDto;
import com.tacniz.visitormanagement.model.MyUser;
import com.tacniz.visitormanagement.service.JwtService;
import com.tacniz.visitormanagement.service.userService.MyUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class MyUserController {
    private final ObjectMapper objectMapper;
    private final MyUserService myUserService;
    private final JwtService jwtService;

    @GetMapping("/all")
    public ResponseEntity<List<MyUserDto>> getAllUsers(){
        List<MyUserDto> userDtos = myUserService.getAllUsers()
                .stream()
                .map(user -> objectMapper.convertValue(user, MyUserDto.class))
                .collect(Collectors.toList());
        return ResponseEntity.ok(userDtos);
    }


    @GetMapping("/username")
    public String getUsername(@RequestParam String token) {
        return jwtService.getUsername(token);
    }

    @PostMapping("/register")
    public ResponseEntity<MyUserDto> saveUser(@RequestBody MyUser user) {

        MyUserDto savedUserDto = myUserService.saveUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedUserDto);
    }
    @PostMapping("/login")
    public String login(){
        return jwtService.getJWTToken();
    }


}
