package com.dogoo.SystemWeighingSas.controller;

import com.dogoo.SystemWeighingSas.common.CommonToken;
import com.dogoo.SystemWeighingSas.model.UserTokenModel;
import com.dogoo.SystemWeighingSas.service.DatabaseKeyService;
import com.dogoo.SystemWeighingSas.unitity.response.Response;
import com.dogoo.SystemWeighingSas.unitity.response.ResponseFactory;
import com.dogoo.SystemWeighingSas.validator.DatabaseKeyValidator;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/o/dogoo/databaseKey")
public class DatabaseKeyAdminController {

    private final DatabaseKeyService databaseKeyService;
    private final DatabaseKeyValidator validator;
    private final CommonToken commonToken;

    public DatabaseKeyAdminController(DatabaseKeyService databaseKeyService, DatabaseKeyValidator validator, CommonToken commonToken) {
        this.databaseKeyService = databaseKeyService;
        this.validator = validator;
        this.commonToken = commonToken;
    }

    @GetMapping("/get-list")
    public Response getListDatabaseKey(@RequestParam(name = "limit", defaultValue = "10", required = false) Integer limit,
                                       @RequestParam(name = "page", defaultValue = "0", required = false) Integer page,
                                       HttpServletRequest request) {
        try {
            UserTokenModel userTokenModel = getUserToken(request);
            return ResponseFactory.getSuccessResponse(Response.SUCCESS,
                    databaseKeyService.getDatabaseKeyByKey(userTokenModel, limit, page));

        } catch (Exception exception) {
            return ResponseFactory.getClientErrorResponse(exception.getMessage());
        }
    }

    private UserTokenModel getUserToken(HttpServletRequest request){
        return commonToken.getUserToken(request);
    }
}
