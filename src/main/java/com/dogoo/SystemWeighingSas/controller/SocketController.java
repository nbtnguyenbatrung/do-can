package com.dogoo.SystemWeighingSas.controller;

import com.dogoo.SystemWeighingSas.service.AccountService;
import com.dogoo.SystemWeighingSas.service.WeighingStationService;
import com.dogoo.SystemWeighingSas.unitity.response.Response;
import com.dogoo.SystemWeighingSas.unitity.response.ResponseFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/o/dogoo/socket")
public class SocketController {

    private final AccountService accountService;
    private final WeighingStationService weighingStationService;
    public SocketController(AccountService accountService,
                            WeighingStationService weighingStationService) {
        this.accountService = accountService;
        this.weighingStationService = weighingStationService;
    }

    @GetMapping("/get-role/{id}")
    public Response getRoleAccount(@PathVariable("id") long id) {
        try {
            return ResponseFactory.getSuccessResponse(Response.SUCCESS,
                    accountService.getAccountByAccountId(id));

        } catch (Exception exception) {
            return ResponseFactory.getClientErrorResponse(exception.getMessage());
        }
    }

    @GetMapping("/get-weighing-station/{code}")
    public Response getWeighingStation(@PathVariable("code") String code) {
        try {
            return ResponseFactory.getSuccessResponse(Response.SUCCESS,
                    weighingStationService.getWeighingStationByCustomerKey(code));

        } catch (Exception exception) {
            return ResponseFactory.getClientErrorResponse(exception.getMessage());
        }
    }

}
