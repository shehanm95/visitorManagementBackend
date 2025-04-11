package com.tacniz.visitormanagement.service.userService.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tacniz.visitormanagement.dto.MyUserDto;
import com.tacniz.visitormanagement.model.MyUser;
import com.tacniz.visitormanagement.repo.MyUserRepo;
import com.tacniz.visitormanagement.service.userService.MyUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MyUserServiceImpl implements MyUserService {
    private final MyUserRepo myUserRepo;
    private final ObjectMapper objectMapper;
    @Lazy
    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<MyUser> getAllUsers(){
        return myUserRepo.findAll();
    }

    public MyUserDto saveUser(MyUser user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return objectMapper.convertValue( myUserRepo.save(user), MyUserDto.class);
    }
    public MyUser getUserByEmail(String email) {
        return myUserRepo.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
}
