package com.tacniz.visitormanagement.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tacniz.visitormanagement.dto.UserDto;
import com.tacniz.visitormanagement.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    private final ObjectMapper objectMapper;

    @GetMapping("/all")
    public ResponseEntity<List<UserDto>> getAllUsers() {
        List<UserDto> users = userService.getAllUsers()
                .stream()
                .map(userEntity -> objectMapper.convertValue(userEntity, UserDto.class))
                .collect(Collectors.toList());

        return ResponseEntity.ok(users);
    }
    @PutMapping("/edit")
    public ResponseEntity<UserDto> ChangeRole(@RequestBody UserDto userDto) {
        return ResponseEntity.ok(objectMapper.convertValue(userService.changeRole(userDto),UserDto.class));
    }
}
