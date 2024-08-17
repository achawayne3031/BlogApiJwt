package com.BlogApiJwt.service;


import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import java.util.Optional;


@Service
@Getter
@Setter
public class EmailService {


    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.sender}")
    private String senderEmail;

    private String to;

    private String from;

    private String subject;

    private String body;

    private String type;


    public EmailService() {

    }


    public void sendMail(){
        switch(type){

            case "text":
                mailWithText();
                break;

            case "attachment":
                mailWithAttachment();
                break;

            case "no attachment":
                mailWithoutAttachement();
                break;

            default:

                break;
        }
    }


    private void mailWithText(){
        SimpleMailMessage message = new SimpleMailMessage();

        message.setFrom(senderEmail);
        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);

        System.out.println(message.toString());

        mailSender.send(message);
    }


    private void mailWithAttachment(){

    }



    private void mailWithoutAttachement(){
        SimpleMailMessage message = new SimpleMailMessage();

        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);

        mailSender.send(message);
    }


}
