package com.dogoo.SystemWeighingSas.controller;

import com.dogoo.SystemWeighingSas.model.UserTokenModel;
import com.dogoo.SystemWeighingSas.service.WeightSlipService;
import com.dogoo.SystemWeighingSas.unitity.response.Response;
import com.dogoo.SystemWeighingSas.unitity.response.ResponseFactory;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/o/dogoo/weight-slip")
public class WeightSlipController {

    private final WeightSlipService weightSlipService;


    public WeightSlipController(WeightSlipService weightSlipService) {
        this.weightSlipService = weightSlipService;
    }

    @GetMapping("/get-list/{code}")
    public Response getListWeightSlip(@PathVariable("code") String code,
                                      @RequestParam(name = "limit", defaultValue = "10", required = false) Integer limit,
                                      @RequestParam(name = "page", defaultValue = "0", required = false) Integer page,
                                      HttpServletRequest request) {
        try {
            return ResponseFactory.getSuccessResponse(Response.SUCCESS,
                    weightSlipService.getWeightSlips(code, limit, page));

        } catch (Exception exception) {
            return ResponseFactory.getClientErrorResponse(exception.getMessage());
        }
    }


}
