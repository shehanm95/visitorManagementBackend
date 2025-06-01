package com.tacniz.visitormanagement.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tacniz.visitormanagement.dto.LoginRequest;
import com.tacniz.visitormanagement.dto.UserDto;
import com.tacniz.visitormanagement.dto.VisitorReqDto;
import com.tacniz.visitormanagement.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
    }  @GetMapping("/get/{email}")
    public ResponseEntity<UserDto> getUserByEmail(@PathVariable String email) {
        return ResponseEntity.ok(userService.getUserByEmail(email));
    }

    @PutMapping("/edit")
    public ResponseEntity<UserDto> ChangeRole(@RequestBody UserDto userDto) {
        return ResponseEntity.ok(objectMapper.convertValue(userService.changeRole(userDto),UserDto.class));
    }

    @PostMapping("/addVisitor")
    public ResponseEntity<UserDto> addVisitor(@RequestBody @Valid VisitorReqDto visitorReqDto){
      return ResponseEntity.ok(userService.addVisitor(visitorReqDto));
    }

    @PostMapping("/visitorLogin")
    public  ResponseEntity<UserDto> loginVisitor(@RequestBody @Valid LoginRequest loginRequest){
        return  ResponseEntity.ok(userService.loginVisitor(loginRequest));
    }

    @PostMapping("/setPhoto/{visitId}")
    public ResponseEntity<UserDto> setPhoto(@RequestBody MultipartFile photo, @PathVariable Long visitId){
        return userService.setPhoto(photo, visitId);
    }
}
