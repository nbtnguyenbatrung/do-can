package com.dogoo.SystemWeighingSas.controller;

import com.dogoo.SystemWeighingSas.common.CommonToken;
import com.dogoo.SystemWeighingSas.model.AccountMapperModel;
import com.dogoo.SystemWeighingSas.model.UserTokenModel;
import com.dogoo.SystemWeighingSas.service.AccountService;
import com.dogoo.SystemWeighingSas.unitity.response.Response;
import com.dogoo.SystemWeighingSas.unitity.response.ResponseFactory;
import com.dogoo.SystemWeighingSas.validator.AccountValidator;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/o/dogoo/account")
public class AccountController {
    private final AccountService accountService;
    private final CommonToken commonToken;
    private final AccountValidator validator;

    public AccountController(AccountService accountService,
                             CommonToken commonToken,
                             AccountValidator validator) {
        this.accountService = accountService;
        this.commonToken = commonToken;
        this.validator = validator;
    }


    @GetMapping("/get-list")
    public Response getListAccount(@RequestParam(name = "limit", defaultValue = "10", required = false) Integer limit,
                                   @RequestParam(name = "page", defaultValue = "0", required = false) Integer page,
                                   HttpServletRequest request) {
        try {
            UserTokenModel userTokenModel = getUserToken(request);
            return ResponseFactory.getSuccessResponse(Response.SUCCESS,
                    accountService.getAccounts(userTokenModel, limit, page));

        } catch (Exception exception) {
            return ResponseFactory.getClientErrorResponse(exception.getMessage());
        }
    }


    @PostMapping("/create")
    public Response createAccount( @RequestBody AccountMapperModel accountDto,
                                   HttpServletRequest request) {
        try {
            Response response = validator.validatorAdd(getUserToken(request), accountDto);
            if (response == null){
                accountService.createAccount(accountDto);
                return ResponseFactory.getSuccessResponse(Response.SUCCESS);
            }

            return response;
        } catch (Exception exception) {
            return ResponseFactory.getClientErrorResponse(exception.getMessage());
        }
    }


    /*
    body{
        "email":"admin@gmail.com",
        "password":"admin"
    }
     */

    @PostMapping("/change-password")
    public Response changePassword( @RequestBody AccountMapperModel accountDto) {
        try {
            accountService.changePasswordAccount(accountDto);
            return ResponseFactory.getSuccessResponse(Response.SUCCESS);
        } catch (Exception exception) {
            return ResponseFactory.getClientErrorResponse(exception.getMessage());
        }
    }


    /*
    body{
        "user":1232132,
        "status":1
    }
    */
    @PostMapping("/change-status")
    public Response changeStatus( @RequestBody AccountMapperModel accountDto) {
        try {
            accountService.activeAccount(accountDto);
            return ResponseFactory.getSuccessResponse(Response.SUCCESS);
        } catch (Exception exception) {
            return ResponseFactory.getClientErrorResponse(exception.getMessage());
        }
    }

    @GetMapping("/find-account")
    public Response getAccount(@RequestParam(name = "accountId", required = true) Integer accountId) {
        try {
            return ResponseFactory.getSuccessResponse(Response.SUCCESS, accountService.getAccountByAccountId(accountId));

        } catch (Exception exception) {
            return ResponseFactory.getClientErrorResponse(exception.getMessage());
        }
    }

    private UserTokenModel getUserToken(HttpServletRequest request){
        return commonToken.getUserToken(request);
    }
}
