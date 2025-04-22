package com.shehan.sec.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("")
public class HomeController {

    @GetMapping("/home")
    public String home(){
        return "this is home";
    }
    @GetMapping("/user")
    public String user(){
        return "this is user home";
    }
    @GetMapping("/admin")
    public String admin(){
        return "this is admin home";
    }

    @GetMapping("/mylogin")
    public String login(){
        return "login page reached";
    }


}
