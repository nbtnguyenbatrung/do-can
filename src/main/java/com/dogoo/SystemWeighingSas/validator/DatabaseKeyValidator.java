package com.dogoo.SystemWeighingSas.validator;

import com.dogoo.SystemWeighingSas.entity.Account;
import com.dogoo.SystemWeighingSas.service.AccountService;
import com.dogoo.SystemWeighingSas.unitity.response.Response;
import com.dogoo.SystemWeighingSas.unitity.response.ResponseFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class DatabaseKeyValidator {

    @Autowired
    private AccountService accountService;

    public ResponseEntity<String> validatorNotFoundKey(String key){
        Account account = accountService.getAccountByKey(key);

        if (account != null)
            return  null;

        return new ResponseEntity<>("Không tồn tại key trong hệ thống", HttpStatus.BAD_REQUEST);
    }
}
