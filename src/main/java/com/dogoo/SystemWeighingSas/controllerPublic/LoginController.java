package com.dogoo.SystemWeighingSas.controllerPublic;

import com.dogoo.SystemWeighingSas.entity.Account;
import com.dogoo.SystemWeighingSas.model.TokenMapperModel;
import com.dogoo.SystemWeighingSas.service.AccountService;
import com.dogoo.SystemWeighingSas.unitity.response.Response;
import com.dogoo.SystemWeighingSas.unitity.response.ResponseFactory;
import com.dogoo.SystemWeighingSas.unitity.token.JwtService;
import com.dogoo.SystemWeighingSas.validator.AccountValidator;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@CrossOrigin
@RestController
@RequestMapping("/public/dogoo")
public class LoginController {

    private final JwtService jwtService;
    private final AccountService accountService;
    private final AccountValidator validator;

    public LoginController(JwtService jwtService,
                           AccountService accountService,
                           AccountValidator validator) {
        this.jwtService = jwtService;
        this.accountService = accountService;
        this.validator = validator;
    }

    @PostMapping("/login")
    public Response login(@RequestBody TokenMapperModel model,
                          HttpServletResponse httpServletResponse) {
        try {

            Response response = validator.validatorLogin(model.getEmail(), model.getPassword());

            if (response == null) {
                Account account = accountService.getAccountByScreenName(model.getEmail());

                String access_token = jwtService.getJWToken(account.getScreenName());
                String refresh_token = jwtService.getRefreshToken(account.getScreenName());

                model.setAccess_token(access_token);
                model.setRefresh_token(refresh_token);
                return ResponseFactory.getSuccessResponse(Response.SUCCESS, model);
            }

            httpServletResponse.setStatus(400);
            return response;
        } catch (Exception exception) {
            httpServletResponse.setStatus(400);
            return ResponseFactory.getClientErrorResponse(exception.getMessage());
        }
    }
}
