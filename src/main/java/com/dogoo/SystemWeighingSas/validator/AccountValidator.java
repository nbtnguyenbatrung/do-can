package com.dogoo.SystemWeighingSas.validator;

import com.dogoo.SystemWeighingSas.entity.Account;
import com.dogoo.SystemWeighingSas.model.AccountMapperModel;
import com.dogoo.SystemWeighingSas.model.UserTokenModel;
import com.dogoo.SystemWeighingSas.service.AccountService;
import com.dogoo.SystemWeighingSas.unitity.response.Response;
import com.dogoo.SystemWeighingSas.unitity.response.ResponseFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountValidator {

    @Autowired
    private AccountService accountService;

    public Response validatorScreenName (String screenName){
        Account account = accountService.getAccountByScreenName(screenName);

        if (account == null){
            return null ;
        }
        return ResponseFactory.getClientErrorResponse("Tên đăng nhập đã được sử dụng");
    }

    public Response validatorEmail (String email){
        Account account = accountService.getAccountByMail(email);

        if (account == null){
            return null ;
        }
        return ResponseFactory.getClientErrorResponse("Email đã được sử dụng");
    }

    public Response validatorKey (String keyModel,
                                  String key){
        Account account = accountService.getAccountByKey(key);

        if (account == null){
            return null ;
        }
        if (keyModel.equals(account.getKey())){
            return null ;
        }
        return ResponseFactory.getClientErrorResponse("key đã được sử dụng");
    }

    public Response validatorAdd (UserTokenModel model,
                                  AccountMapperModel accountDto){
        Response responseScreenName = validatorScreenName(accountDto.getScreenName());
        Response responseEmail = validatorEmail(accountDto.getEmail());
        Response responseKey = validatorKey(model.getKey(), accountDto.getKey());

        if (responseScreenName != null){
            return responseScreenName;
        }

        if (responseEmail != null){
            return responseEmail;
        }
        if (responseKey != null){
            return responseKey;
        }

        return null;
    }
}
