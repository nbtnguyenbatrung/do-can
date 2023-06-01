package com.dogoo.SystemWeighingSas.controller;

import com.dogoo.SystemWeighingSas.model.WeightSlipCriteria;
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
                                      @RequestParam(name = "pageSize", defaultValue = "10", required = false) Integer pageSize,
                                      @RequestParam(name = "page", defaultValue = "0", required = false) Integer page,
                                      WeightSlipCriteria weightSlipCriteria,
                                      HttpServletRequest request) {
        try {

            weightSlipCriteria.setDatabaseKey(code);
            return ResponseFactory.getSuccessResponse(Response.SUCCESS,
                    weightSlipService.getWeightSlipsFilter(pageSize, page, weightSlipCriteria));

        } catch (Exception exception) {
            return ResponseFactory.getClientErrorResponse(exception.getMessage());
        }
    }

    @GetMapping("/get-list-soxe/{code}")
    public Response getListWeightSlipSoXe(@PathVariable("code") String code) {
        try {
            return ResponseFactory.getSuccessResponse(Response.SUCCESS,
                    weightSlipService.findSoXeDistinct(code));

        } catch (Exception exception) {
            return ResponseFactory.getClientErrorResponse(exception.getMessage());
        }
    }
    @GetMapping("/get-list-tenhang/{code}")
    public Response getListWeightSlipTenHang(@PathVariable("code") String code) {
        try {
            return ResponseFactory.getSuccessResponse(Response.SUCCESS,
                    weightSlipService.findTenHangDistinct(code));

        } catch (Exception exception) {
            return ResponseFactory.getClientErrorResponse(exception.getMessage());
        }
    }
    @GetMapping("/get-list-khachhang/{code}")
    public Response getListWeightSlipKhachHang(@PathVariable("code") String code) {
        try {
            return ResponseFactory.getSuccessResponse(Response.SUCCESS,
                    weightSlipService.findKhachHangDistinct(code));

        } catch (Exception exception) {
            return ResponseFactory.getClientErrorResponse(exception.getMessage());
        }
    }

}
