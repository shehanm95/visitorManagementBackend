package com.tacniz.visitormanagement.service;

import com.tacniz.visitormanagement.dto.ChangeRoleRequest;
import com.tacniz.visitormanagement.dto.UserDto;
import com.tacniz.visitormanagement.model.UserEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;


public interface UserService {
    List<UserEntity> getAllUsers();

    UserEntity changeRole(UserDto userDto);
}
