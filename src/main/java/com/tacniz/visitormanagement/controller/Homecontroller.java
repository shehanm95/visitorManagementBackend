package com.tacniz.visitormanagement.controller;


import com.tacniz.visitormanagement.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequestMapping("")
@RestController
@RequiredArgsConstructor
public class Homecontroller {



    @GetMapping("")
    public String home(){
        return "this is home";
    }


}
