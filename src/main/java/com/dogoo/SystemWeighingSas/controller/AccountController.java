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
import javax.servlet.http.HttpServletResponse;

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
    public Response createAccount(@RequestBody AccountMapperModel accountDto,
                                  HttpServletRequest request,
                                  HttpServletResponse httpServletResponse) {
        try {
            Response response = validator.validatorAdd(getUserToken(request), accountDto);
            if (response == null) {
                accountService.createAccount(getUserToken(request), accountDto);
                return ResponseFactory.getSuccessResponse(Response.SUCCESS);
            }

            httpServletResponse.setStatus(400);
            return response;
        } catch (Exception exception) {
            httpServletResponse.setStatus(400);
            return ResponseFactory.getClientErrorResponse(exception.getMessage());
        }
    }

    @PutMapping("/update/{id}")
    public Response updateAccount(@PathVariable("id") long id,
                                  @RequestBody AccountMapperModel accountDto,
                                  HttpServletRequest request,
                                  HttpServletResponse httpServletResponse) {
        try {
            Response response = validator.validatorUpdate(id, getUserToken(request), accountDto);
            if (response == null) {
                accountService.updateAccount(id, accountDto);
                return ResponseFactory.getSuccessResponse(Response.SUCCESS);
            }

            httpServletResponse.setStatus(400);
            return response;
        } catch (Exception exception) {
            httpServletResponse.setStatus(400);
            return ResponseFactory.getClientErrorResponse(exception.getMessage());
        }
    }


    /*
    body{
        "email":"admin@gmail.com",
        "password":"admin"
    }
     */

    @PostMapping("/change-password/{id}")
    public Response changePassword(@PathVariable("id") long id,
                                   @RequestBody AccountMapperModel accountDto,
                                   HttpServletResponse httpServletResponse) {
        try {
            Response response = validator.validatorChangePassword(id, accountDto);

            if (response == null){
                accountService.changePasswordAccount(id, accountDto);
                return ResponseFactory.getSuccessResponse(Response.SUCCESS);
            }

            httpServletResponse.setStatus(400);
            return response;
        } catch (Exception exception) {
            httpServletResponse.setStatus(400);
            return ResponseFactory.getClientErrorResponse(exception.getMessage());
        }
    }


    /*
    body{
        "user":1232132,
        "status":1
    }
    */
    @PostMapping("/change-status/{id}")
    public Response changeStatus(@PathVariable("id") long id,
                                 @RequestBody AccountMapperModel accountDto) {
        try {
            accountService.activeAccount(id, accountDto);
            return ResponseFactory.getSuccessResponse(Response.SUCCESS);
        } catch (Exception exception) {
            return ResponseFactory.getClientErrorResponse(exception.getMessage());
        }
    }

    @GetMapping("/find-account/{id}")
    public Response getAccount(@PathVariable("id") long id) {
        try {
            return ResponseFactory.getSuccessResponse(Response.SUCCESS,
                    accountService.getAccountByAccountId(id));

        } catch (Exception exception) {
            return ResponseFactory.getClientErrorResponse(exception.getMessage());
        }
    }

    @PostMapping("/change-role/{id}")
    public Response changeRole(@PathVariable("id") long id,
                               @RequestBody AccountMapperModel accountDto) {
        try {
            accountService.changeRole(id, accountDto);
            return ResponseFactory.getSuccessResponse(Response.SUCCESS);
        } catch (Exception exception) {
            return ResponseFactory.getClientErrorResponse(exception.getMessage());
        }
    }

    private UserTokenModel getUserToken(HttpServletRequest request) {
        return commonToken.getUserToken(request);
    }
}
