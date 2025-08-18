package com.tacniz.visitormanagement.service.impl;

import com.tacniz.visitormanagement.dto.OptChecker;
import com.tacniz.visitormanagement.model.OTPObj;
import com.tacniz.visitormanagement.model.UserEntity;
import com.tacniz.visitormanagement.repo.OPTRepo;
import com.tacniz.visitormanagement.repo.UserEntityRepository;
import com.tacniz.visitormanagement.service.EmailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class EmailServiceImpl implements EmailService {

    @Autowired
    private JavaMailSender mailSender;
    @Autowired
    private OPTRepo optRepo;
    @Autowired
    @Lazy
    private UserEntityRepository userEntityRepository;


    private void sendSimpleEmail(String toEmail, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject(subject);
        message.setText(body);
        message.setFrom("shehanmaleesha4884@gmail.com");
        mailSender.send(message);
    }

    @Override
    @Transactional
    @Async
    public void sendFourDigitAuthenticationEmail(String toEmail) {
        if(toEmail == null || toEmail.isEmpty() || toEmail.isBlank()){throw new IllegalArgumentException("EmailService: must provide a valid Email");}
        String digits = generateRandom4DigitString();
        saveEmailObject(toEmail, digits);
        String htmlBody = "<div style=\"font-family: Arial, sans-serif; padding: 20px; max-width: 600px; margin: auto; border: 1px solid #ddd; border-radius: 8px;\">" +
                "<h2 style=\"color: #325DA7;\">Visitor Management Authentication</h2>" +
                "<p>Hello,</p>" +
                "<p>Your authentication code is:</p>" +
                "<div style=\"font-size: 28px; font-weight: bold; color: white; background-color: #325DA7; display: inline-block; padding: 10px 20px; border-radius: 5px;\">" +
                digits +
                "</div>" +
                "<p style=\"margin-top: 20px; color: #333;\">This code is valid for <strong>1 minute and 30 seconds</strong>. Please do not share it with anyone.</p>" +
                "<p style=\"margin-top: 30px; font-size: 12px; color: #777;\">Thank you,<br/>Visitor Management System</p>" +
                "</div>";

        System.out.println("Email sent to " + toEmail + " with digits " + digits);
        sendHtmlEmail(toEmail, "Your Authentication Code", htmlBody);
    }

    @Override
    @Transactional
    public boolean checkOpt(OptChecker optChecker) {
        if(optRepo.findByEmail(optChecker.getEmail()).isPresent()){
            OTPObj optObj = optRepo.findByEmail(optChecker.getEmail()).get();
            if(optObj.getDigits().equals(optChecker.getDigits())){
                optRepo.deleteByEmail(optChecker.getEmail());
                UserEntity userEntity = userEntityRepository.findByEmail(optChecker.getEmail()).orElseThrow(()-> new IllegalArgumentException("User not Exist in the database with this Email"));
                userEntity.setIsEmailVerified(true);
                userEntityRepository.save(userEntity);
                return true;
            }
        }
        return false;

    }

    @Transactional
    private void saveEmailObject(String toEmail, String digits) {
        if(optRepo.findByEmail(toEmail).isPresent()){
            optRepo.deleteByEmail(toEmail);
        }
        OTPObj optObj = OTPObj.builder()
                .email(toEmail)
                .digits(digits)
                .build();

        optRepo.save(optObj);
    }

    private void sendHtmlEmail(String toEmail, String subject, String htmlBody) {
        MimeMessage message = mailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            helper.setTo(toEmail);
            helper.setSubject(subject);
            helper.setFrom("shehanmaleesha4884@gmail.com");
            helper.setText(htmlBody, true); // true = HTML
            mailSender.send(message);
        } catch (MessagingException e) {
            throw new IllegalArgumentException("Email Service : cannot send the code to the given email");
        }
    }

    private static String generateRandom4DigitString() {
        Random random = new Random();
        int number = random.nextInt(10000); // Generates a number from 0 to 9999
        return String.format("%04d", number); // Formats with leading zeros if needed
    }
}
