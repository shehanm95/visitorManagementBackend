package com.tacniz.visitormanagement.service.userService;

import com.tacniz.visitormanagement.dto.MyUserDto;
import com.tacniz.visitormanagement.model.MyUser;

import java.util.List;

public interface MyUserService {
    public List<MyUser> getAllUsers();

    public MyUserDto saveUser(MyUser user);
    public MyUser getUserByEmail(String email);
}
