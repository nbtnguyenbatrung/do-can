package com.dogoo.SystemWeighingSas.controllerPublic;

import com.dogoo.SystemWeighingSas.common.MailUtil;
import com.dogoo.SystemWeighingSas.entity.Account;
import com.dogoo.SystemWeighingSas.model.UserTokenModel;
import com.dogoo.SystemWeighingSas.service.AccountService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.MessagingException;
import java.util.List;

@RestController
@RequestMapping("/public/dogoo/mail")
public class MailController {

    private final MailUtil mailUtil;
    private final AccountService accountService;

    public MailController(MailUtil mailUtil, AccountService accountService) {
        this.mailUtil = mailUtil;
        this.accountService = accountService;
    }

    @GetMapping("/send-mail")
    public ResponseEntity<List<Account>> getSendMail() throws MessagingException {
        UserTokenModel model = new UserTokenModel();
        model.setKey("");
        return ResponseEntity.status(200).body(
                accountService.getAccounts());
    }
}
