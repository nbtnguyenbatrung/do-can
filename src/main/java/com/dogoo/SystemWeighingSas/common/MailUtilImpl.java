package com.dogoo.SystemWeighingSas.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class MailUtilImpl implements MailUtil {

    @Autowired
    private JavaMailSender javaMailSender;
    @Autowired
    private SpringTemplateEngine templateEngine;

    @Value("${spring.mail.username}")
    private String sender;
    @Value("${dogoo.link}")
    private String link;

    @Override
    public void sendSimpleMail(String email,
                               String subject,
                               Map<String, Object> model) throws MessagingException {

        MimeMessage message = javaMailSender.createMimeMessage();

        MimeMessageHelper helper = new MimeMessageHelper(message, true, "utf-8");

        helper.setFrom("Dogoo Can <" + sender + ">");
        helper.setTo(email);
        helper.setSubject(subject);

        model.put("link", link);
        Context context = new Context();
        context.setVariables(model);
        String html = templateEngine.process("index", context);
        helper.setText(html, true);

//        message.setContent(msgBody, "text/html; charset=utf-8");
        /*helper.setText(msgBody);*/
        javaMailSender.send(helper.getMimeMessage());
        log.info("Mail Sent Successfully...");
    }
}
