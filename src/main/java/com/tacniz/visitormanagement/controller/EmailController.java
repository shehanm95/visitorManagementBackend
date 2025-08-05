package com.tacniz.visitormanagement.controller;

import com.tacniz.visitormanagement.dto.OptChecker;
import com.tacniz.visitormanagement.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/mail")
@RequiredArgsConstructor
public class EmailController {

    private final EmailService emailService;

    @GetMapping("/resendOpt/{email}")
    public void sendMail(@PathVariable String email) {
       emailService.sendFourDigitAuthenticationEmail(email);
    }

    @PostMapping("/checkOpt")
    public void checkOpt(@RequestBody OptChecker optChecker){
        if(!emailService.checkOpt(optChecker)){
            throw new IllegalArgumentException("Entered 4 digit OPT code is not valid");
        }
    }
}
