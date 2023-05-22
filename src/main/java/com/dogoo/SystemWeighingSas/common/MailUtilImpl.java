package com.dogoo.SystemWeighingSas.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
@Slf4j
public class MailUtilImpl implements MailUtil{

    @Autowired
    private JavaMailSender javaMailSender;

    @Value("${spring.mail.username}") private String sender;

    @Override
    public String sendSimpleMail(String email, String msgBody, String subject) throws MessagingException {

//        try {

//            SimpleMailMessage mailMessage
//                    = new SimpleMailMessage();
//
//            // Setting up necessary details
//            mailMessage.setFrom(sender);
//            mailMessage.setTo(email);
//            mailMessage.setText(msgBody);
//            mailMessage.setSubject(subject);
//
//            // Sending the mail
//            javaMailSender.send(mailMessage);

        MimeMessage message = javaMailSender.createMimeMessage();

        MimeMessageHelper helper = new MimeMessageHelper(message, true, "utf-8");

        helper.setFrom(sender);
        helper.setTo(email);
        helper.setSubject(subject);
        helper.setText(msgBody);

            log.info("Mail Sent Successfully...");
            return "Mail Sent Successfully...";

//        }
//        catch (Exception e) {
//            log.error("Error while Sending Mail");
//            return "Error while Sending Mail";
//        }
    }
}
