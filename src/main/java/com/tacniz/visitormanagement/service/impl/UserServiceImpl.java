package com.tacniz.visitormanagement.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tacniz.visitormanagement.dto.UserDto;
import com.tacniz.visitormanagement.model.UserEntity;
import com.tacniz.visitormanagement.repo.UserEntityRepository;
import com.tacniz.visitormanagement.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserEntityRepository userEntityRepository;
    private final ObjectMapper objectMapper;
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

}
