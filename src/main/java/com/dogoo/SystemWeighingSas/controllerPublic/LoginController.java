package com.dogoo.SystemWeighingSas.controllerPublic;

import com.dogoo.SystemWeighingSas.entity.Account;
import com.dogoo.SystemWeighingSas.model.TokenMapperModel;
import com.dogoo.SystemWeighingSas.service.AccountService;
import com.dogoo.SystemWeighingSas.unitity.token.JwtService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/public/dogoo")
public class LoginController {

    private final JwtService jwtService;
    private final AccountService accountService;

    public LoginController(JwtService jwtService, AccountService accountService) {
        this.jwtService = jwtService;
        this.accountService = accountService;
    }

    @PostMapping("/login")
    public ResponseEntity<TokenMapperModel> login(@RequestBody TokenMapperModel model) {
        try {

            Account account = accountService.getAccountByScreenName(model.getEmail());

            if (account != null){
                String access_token = jwtService.getJWToken(account.getEmail());
                String refresh_token = jwtService.getRefreshToken(account.getEmail());

                model.setAccess_token(access_token);
                model.setRefresh_token(refresh_token);
                return new ResponseEntity<>(model, HttpStatus.OK);
            }

            return new ResponseEntity<>(new TokenMapperModel(), HttpStatus.UNAUTHORIZED);
        } catch (Exception exception) {
            return new ResponseEntity<>(new TokenMapperModel(), HttpStatus.UNAUTHORIZED);
        }
    }
}
