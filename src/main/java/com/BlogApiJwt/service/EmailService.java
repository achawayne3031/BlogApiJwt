package com.BlogApiJwt.service;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.io.IOException;
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

    private Context context;

    private String htmlTemplate;

    private final TemplateEngine templateEngine;

    public EmailService(TemplateEngine templateEngine) {
        this.templateEngine = templateEngine;
    }

    public void sendMail() {
        switch (type) {

            case "text":
                mailWithText();
                break;

            case "html":
                try {
                    mailWithHtml();
                } catch (MessagingException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                break;

            case "htmlWithAttachment":

                try {
                    mailWithHtmAndAttachment();
                } catch (MessagingException | IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

                break;

            case "no attachment":
                mailWithoutAttachement();
                break;

            default:

                break;
        }
    }

    private void mailWithText() {
        SimpleMailMessage message = new SimpleMailMessage();

        message.setFrom(senderEmail);
        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);

        mailSender.send(message);
    }

    private void mailWithHtml() throws MessagingException {

        MimeMessage message = mailSender.createMimeMessage();

        // Process the template with the given context
        String htmlContent = templateEngine.process(htmlTemplate, context);

        message.setFrom(new InternetAddress(senderEmail));
        message.setRecipients(MimeMessage.RecipientType.TO, to);
        message.setSubject(subject);
        message.setContent(htmlContent, "text/html; charset=utf-8");

        mailSender.send(message);

    }

    private void mailWithHtmAndAttachment() throws MessagingException, IOException {

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        // Process the template with the given context
        String htmlContent = templateEngine.process(htmlTemplate, context);

        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(htmlContent, true);

        ClassPathResource classPathResource = new ClassPathResource("blog.sql");
        helper.addAttachment(classPathResource.getFilename(), classPathResource);

        mailSender.send(message);

    }

    private void mailWithoutAttachement() {
        SimpleMailMessage message = new SimpleMailMessage();

        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);

        mailSender.send(message);
    }

}
