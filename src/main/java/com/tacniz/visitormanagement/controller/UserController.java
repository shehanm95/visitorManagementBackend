package com.tacniz.visitormanagement.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tacniz.visitormanagement.dto.LoginRequest;
import com.tacniz.visitormanagement.dto.RegisterRequest;
import com.tacniz.visitormanagement.dto.UserDto;
import com.tacniz.visitormanagement.dto.VisitorReqDto;
import com.tacniz.visitormanagement.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
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
    }

    @GetMapping("/get/{email}")
    public ResponseEntity<UserDto> getUserByEmail(@PathVariable String email) {
        return ResponseEntity.ok(userService.getUserByEmail(email));
    }

    @GetMapping("/getById/{id}")
    public ResponseEntity<UserDto> getUserByEmail(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @PutMapping("/edit")
    public ResponseEntity<UserDto> editUser(@RequestBody RegisterRequest userDto) {
        return ResponseEntity.ok(objectMapper.convertValue(userService.editUser(userDto),UserDto.class));
    }

    @PostMapping("/addVisitor")
    public ResponseEntity<UserDto> addVisitor(@RequestBody @Valid VisitorReqDto visitorReqDto){
      return ResponseEntity.ok(userService.addVisitor(visitorReqDto));
    }

    @PostMapping("/visitorLogin")
    public  ResponseEntity<UserDto> loginVisitor(@RequestBody @Valid LoginRequest loginRequest){
        return  ResponseEntity.ok(userService.loginVisitor(loginRequest));
    }


    @GetMapping("findUser/{input}")
    public ResponseEntity<List<UserDto>> findUser(@PathVariable String input){
        return ResponseEntity.ok(userService.findUser(input));
    }

    @PostMapping(value = "/{id}/profile-image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<UserDto> saveProfileImage(
            @PathVariable Long id,
            @RequestPart("image") MultipartFile imageFile) {

        // Validate the file
        if (imageFile.isEmpty()) {
            throw new IllegalArgumentException("Image file cannot be empty");
        }

        // Validate file type
        String contentType = imageFile.getContentType();
        if (contentType == null || !contentType.startsWith("image/")) {
            throw new IllegalArgumentException("Only image files are allowed");
        }

        // Process the image
        try {
            UserDto updatedUser = userService.saveImage(id, imageFile);
            return ResponseEntity.ok(updatedUser);
        } catch (Exception e) {
            throw new IllegalArgumentException("Failed to save image", e);
        }
    }

    @GetMapping("/getImage/{imageName}")
    public ResponseEntity<Resource> getImage(@PathVariable String imageName){
        return userService.getImage(imageName);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteUser(@PathVariable Long id){
        userService.deleteUser(id);
    }
}

