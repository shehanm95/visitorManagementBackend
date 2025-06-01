package com.tacniz.visitormanagement.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tacniz.visitormanagement.dto.LoginRequest;
import com.tacniz.visitormanagement.dto.UserDto;
import com.tacniz.visitormanagement.dto.VisitorReqDto;
import com.tacniz.visitormanagement.model.Role;
import com.tacniz.visitormanagement.model.UserEntity;
import com.tacniz.visitormanagement.repo.UserEntityRepository;
import com.tacniz.visitormanagement.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserEntityRepository userEntityRepository;
    private final ObjectMapper objectMapper;
    private final PasswordEncoder passwordEncoder;
    private final String IMAGE_DIRECTORY = "profilePic/";
    @Override
    public List<UserEntity> getAllUsers() {
        return userEntityRepository.findAll().stream()
                .peek(user -> user.setImagePath("https://images.ctfassets.net/h6goo9gw1hh6/2sNZtFAWOdP1lmQ33VwRN3/24e953b920a9cd0ff2e1d587742a2472/1-intro-photo-final.jpg?w=1200&h=992&fl=progressive&q=70&fm=jpg")).toList();
    }

    @Override
    public UserEntity changeRole(UserDto userDto) {
        return userEntityRepository.findById(userDto.getId())
                .map(user -> {
                    UserEntity convertedUser = objectMapper.convertValue(userDto, UserEntity.class);
                    if(!userDto.getEmail().equals(user.getEmail())){
                        if(userEntityRepository.existsByEmail(userDto.getEmail())){
                            throw new IllegalArgumentException("New email address you entered is already exist.");
                        }
                    }

                    convertedUser.setPassword(user.getPassword());
                    return userEntityRepository.save(convertedUser);
                })
                .orElseThrow(() -> new IllegalArgumentException("User doesn't exist in the database"));
    }

    @Override
    public UserDto addVisitor(VisitorReqDto visitorReqDto) {
        if(userEntityRepository.existsByEmail(visitorReqDto.getEmail())){
            throw new IllegalArgumentException("User Already Exist in the database");
        }

        UserEntity user = objectMapper.convertValue(visitorReqDto, UserEntity.class);
        user.setRole(Role.ROLE_VISITOR);
        user.setIsEmailVerified(false);
        user.setIsPhoneNumberVerified(false);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return objectMapper.convertValue(userEntityRepository.save(user),UserDto.class);
    }

    @Override
    public UserDto loginVisitor(LoginRequest loginRequest) {
        UserEntity user = userEntityRepository.findByEmail(loginRequest.getEmail()).orElseThrow(()-> new IllegalArgumentException("Wrong User Email, Not exist in the database"));
        if(passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())){
            return objectMapper.convertValue(user,UserDto.class);
        }else {
            throw new IllegalArgumentException("User is exist in the database but the entered password is wrong");
        }
    }

    @Override
    public ResponseEntity<UserDto> setPhoto(MultipartFile photo, Long visitId) {
        return null;
    }

    @Override
    public UserDto getUserByEmail(String email) {
        if (email == null || email.isEmpty()) {
            throw new IllegalArgumentException("Email cannot be null");
        }
        UserEntity user = userEntityRepository.findByEmail(email).orElseThrow(()->new IllegalArgumentException("User not found in the database"));
        return objectMapper.convertValue(user, UserDto.class);
    }

}
