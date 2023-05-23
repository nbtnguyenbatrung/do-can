package com.dogoo.SystemWeighingSas.controllerPublic;

import com.dogoo.SystemWeighingSas.common.MailUtil;
import com.dogoo.SystemWeighingSas.common.generalPassword.PwdGenerator;
import com.dogoo.SystemWeighingSas.service.AccountService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.MessagingException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/public/dogoo/mail")
public class MailController {

    private final MailUtil mailUtil;
    private final AccountService accountService;
    private final PwdGenerator pwdGenerator;

    public MailController(MailUtil mailUtil,
                          AccountService accountService,
                          PwdGenerator pwdGenerator) {
        this.mailUtil = mailUtil;
        this.accountService = accountService;
        this.pwdGenerator = pwdGenerator;
    }

    @GetMapping("/send-mail")
    public void getSendMail() throws MessagingException {

        mailUtil.sendSimpleMail("uocmotnguoi@gmail.com" ,
                "test", new HashMap<>());

//        System.out.println("trung " + pwdGenerator.getPassword());
//
//        String s = "Nguyễnthanhnam";
//        int lastIndex = s.lastIndexOf(" ");
//        List<String> list = Arrays.asList(s.split(" ", lastIndex));
//        System.out.println("trung " + list.toString());
//        String sc = list.stream().map(s1 -> s1.substring(0,1)).collect(Collectors.joining(""));
//        System.out.println("trung sc " + sc);
    }
}
