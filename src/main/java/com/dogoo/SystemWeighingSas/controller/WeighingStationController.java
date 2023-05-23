package com.dogoo.SystemWeighingSas.controller;

import com.dogoo.SystemWeighingSas.entity.Customer;
import com.dogoo.SystemWeighingSas.entity.WeighingStation;
import com.dogoo.SystemWeighingSas.model.WeighingStationMapperModel;
import com.dogoo.SystemWeighingSas.service.WeighingStationService;
import com.dogoo.SystemWeighingSas.unitity.response.Response;
import com.dogoo.SystemWeighingSas.unitity.response.ResponseFactory;
import com.dogoo.SystemWeighingSas.validator.WeighingStationValidator;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/o/dogoo/weighing-station")
public class WeighingStationController {

    private final WeighingStationService weighingStationService;
    private final WeighingStationValidator validator;

    public WeighingStationController(WeighingStationService weighingStationService,
                                     WeighingStationValidator validator) {
        this.weighingStationService = weighingStationService;
        this.validator = validator;
    }

    @PostMapping("/add")
    public Response addWeighingStation(@RequestBody WeighingStationMapperModel model) {
        try {
            WeighingStation to = weighingStationService.addWeighingStation(model);
            return ResponseFactory.getSuccessResponse(Response.SUCCESS, to);
        } catch (Exception exception) {
            return ResponseFactory.getClientErrorResponse(exception.getMessage());
        }
    }

    @PutMapping("/update/{id}")
    public Response updateWeighingStation(@PathVariable("id") long id,
                                          @RequestBody WeighingStationMapperModel model,
                                          HttpServletResponse httpServletResponse) {
        try {
            Response response = validator.validatorExits(id);
            if (response == null) {
                WeighingStation to = weighingStationService.updateWeighingStation(id, model);
                return ResponseFactory.getSuccessResponse(Response.SUCCESS, to);
            }
            httpServletResponse.setStatus(404);
            return response;
        } catch (Exception exception) {
            return ResponseFactory.getClientErrorResponse(exception.getMessage());
        }
    }

    @DeleteMapping("/delete/{id}")
    public Response deleteWeighingStation(@PathVariable("id") long id,
                                          HttpServletResponse httpServletResponse) {
        try {
            Response response = validator.validatorExits(id);
            if (response == null) {
                weighingStationService.deleteWeighingStation(id);
                return ResponseFactory.getSuccessResponse(Response.SUCCESS);
            }

            httpServletResponse.setStatus(404);
            return response;
        } catch (Exception exception) {
            return ResponseFactory.getClientErrorResponse(exception.getMessage());
        }
    }

    @GetMapping("/get/{id}")
    public Response getWeighingStation(@PathVariable("id") long id,
                                       HttpServletResponse httpServletResponse) {
        try {
            Response response = validator.validatorExits(id);
            if (response == null) {
                WeighingStation to = weighingStationService.getWeighingStation(id);
                return ResponseFactory.getSuccessResponse(Response.SUCCESS, to);
            }

            httpServletResponse.setStatus(404);
            return response;
        } catch (Exception exception) {
            return ResponseFactory.getClientErrorResponse(exception.getMessage());
        }
    }

    @GetMapping("/get-list")
    public Response getListWeighingStation(@RequestParam(name = "pageSize", defaultValue = "10", required = false) Integer pageSize,
                                           @RequestParam(name = "page", defaultValue = "0", required = false) Integer page) {
        try {
            return ResponseFactory.getSuccessResponse(Response.SUCCESS,
                    weighingStationService.getWeighingStations(pageSize, page));

        } catch (Exception exception) {
            return ResponseFactory.getClientErrorResponse(exception.getMessage());
        }
    }
}
