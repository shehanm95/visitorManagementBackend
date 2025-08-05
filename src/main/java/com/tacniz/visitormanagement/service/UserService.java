package com.tacniz.visitormanagement.service;

import com.tacniz.visitormanagement.dto.LoginRequest;
import com.tacniz.visitormanagement.dto.UserDto;
import com.tacniz.visitormanagement.dto.VisitorReqDto;
import com.tacniz.visitormanagement.model.UserEntity;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


public interface UserService {
    List<UserEntity> getAllUsers();

    UserEntity changeRole(UserDto userDto);

    UserDto addVisitor(VisitorReqDto visitorReqDto);

    UserDto loginVisitor(LoginRequest loginRequest);

    UserDto getUserByEmail(String email);

    List<UserDto> findUser(String input);

    UserDto saveImage(Long id, MultipartFile image);

    ResponseEntity<Resource> getImage(String imageName);

    UserEntity saveImageInternal(UserEntity user, MultipartFile image);

    UserDto getUserById(Long id);

    void deleteUser(Long id);
}
