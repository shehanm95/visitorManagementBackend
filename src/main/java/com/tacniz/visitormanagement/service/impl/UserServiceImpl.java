package com.tacniz.visitormanagement.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tacniz.visitormanagement.dto.LoginRequest;
import com.tacniz.visitormanagement.dto.UserDto;
import com.tacniz.visitormanagement.dto.VisitorReqDto;
import com.tacniz.visitormanagement.mapper.UserMapper;
import com.tacniz.visitormanagement.model.Role;
import com.tacniz.visitormanagement.model.UserEntity;
import com.tacniz.visitormanagement.repo.UserEntityRepository;
import com.tacniz.visitormanagement.service.EmailService;
import com.tacniz.visitormanagement.service.ImageService;
import com.tacniz.visitormanagement.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserEntityRepository userEntityRepository;
    private final ObjectMapper objectMapper;
    private final PasswordEncoder passwordEncoder;
    private final ImageService imageService;
    private final EmailService emailService;
    private final UserMapper userMapper;


    private final String IMAGE_DIRECTORY = "profilePic/";
    @Override
    public List<UserEntity> getAllUsers() {
        return userEntityRepository.findAll().stream()
                .toList();
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
        emailService.sendFourDigitAuthenticationEmail(user.getEmail());
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
    public UserDto getUserByEmail(String email) {
        if (email == null || email.isEmpty()) {
            throw new IllegalArgumentException("Email cannot be null");
        }
        UserEntity user = userEntityRepository.findByEmail(email).orElseThrow(()->new IllegalArgumentException("User not found in the database"));
        return objectMapper.convertValue(user, UserDto.class);
    }

    @Override
    public List<UserDto> findUser(String input) {
        // Try to parse input as Long (for ID search)
        try {
            Long id = Long.parseLong(input);
            Optional<UserEntity> user = userEntityRepository.findById(id);
            if (user.isPresent()) {
                return List.of(convertToDto(user.get()));
            }
        } catch (NumberFormatException e) {
            // Input is not a number, continue with text search
        }

        // Search by firstName, lastName, or email containing the input (case insensitive)
        // Limit results to 10
        Pageable limit = PageRequest.of(0, 10);
        List<UserEntity> users = userEntityRepository
                .findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCaseOrEmailContainingIgnoreCase(
                        input, input, input, limit
                );

        return users.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public UserDto saveImage(Long id, MultipartFile image) {
        UserEntity user = userEntityRepository.findById(id).orElseThrow(()-> new IllegalArgumentException("UserService : image cannot save - user not found in the database"));
        user  = saveImageInternal(user, image);
        return convertToDto(user);
    }

    @Override
    public ResponseEntity<Resource> getImage(String imageName) {
        return imageService.getImage(IMAGE_DIRECTORY,imageName);
    }

    @Override
    public UserEntity saveImageInternal(UserEntity user, MultipartFile image) {
        // delete if a image
        if(user.getImagePath() != null){
            imageService.deleteImage(IMAGE_DIRECTORY,user.getImagePath());
        }

        String imagePath = imageService.saveImage(IMAGE_DIRECTORY,user.getId().toString(),image);
        user.setImagePath(imagePath);
        return userEntityRepository.save(user);
    }

    @Override
    public UserDto getUserById(Long id) {
        UserEntity user = userEntityRepository.findById(id).orElseThrow(()-> new IllegalArgumentException("user not found in the database"));
        return (userMapper.toDto(user));
    }

    @Override
    public void deleteUser(Long id) {
        try {
            userEntityRepository.deleteById(id);
        }catch (Exception e){
            throw new IllegalArgumentException("UserService : cannot delete user with sent id");
        }
    }


    // Helper method to convert UserEntity to UserDto
    private UserDto convertToDto(UserEntity user) {
        return UserDto.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .imagePath(user.getImagePath())
                .phoneNumber(user.getPhoneNumber())
                .role(user.getRole().toString())
                .build();
    }

}
