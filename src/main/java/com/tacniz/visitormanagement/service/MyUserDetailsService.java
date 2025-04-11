package com.tacniz.visitormanagement.service;

import com.tacniz.visitormanagement.model.MyUser;
import com.tacniz.visitormanagement.service.userService.MyUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Lazy
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Lazy
    @Autowired
    private MyUserService myUserService;



    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        MyUser myUser = myUserService.getUserByEmail(email);

        if (myUser == null) {
            throw new UsernameNotFoundException("User not found with email: " + email);
        }

        return User.builder()
                .username(myUser.getEmail())
                .password(myUser.getPassword()) // Make sure this is already encoded in DB
                .roles(myUser.getUserRole().name()) // Spring Security uses roles for authorization
                .build();
    }
}

