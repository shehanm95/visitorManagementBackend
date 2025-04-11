package com.tacniz.visitormanagement.controller;


import com.tacniz.visitormanagement.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequestMapping("")
@RestController
@RequiredArgsConstructor
public class Homecontroller {

    private final JwtService jwtService;

    @GetMapping("")
    public String home(){
        return "this is home";
    }


    @PostMapping("/login")
    public String login(){
        return jwtService.getJWTToken();
    }

    @GetMapping("/username")
    public String getUsername(@RequestParam String token) {
        return jwtService.getUsername(token);
    }
}
