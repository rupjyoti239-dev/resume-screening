package com.example.resumeScreening.Be.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender javaMailSender;


    public void sendOtp(String to,String subject,String body){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);
        message.setFrom("rupjyotisarma706@gmail.com");

        try{
            javaMailSender.send(message);
            System.out.println("Email sent successfully !");
        }catch (Exception e){
            System.out.println("Exception: " + e.getMessage());
        }
    }
}
