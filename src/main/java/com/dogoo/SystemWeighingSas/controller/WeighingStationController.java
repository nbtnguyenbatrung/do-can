package com.dogoo.SystemWeighingSas.controller;

import com.dogoo.SystemWeighingSas.common.CommonToken;
import com.dogoo.SystemWeighingSas.entity.WeighingStation;
import com.dogoo.SystemWeighingSas.model.AccountMapperModel;
import com.dogoo.SystemWeighingSas.model.UserTokenModel;
import com.dogoo.SystemWeighingSas.model.WeighingStationMapperModel;
import com.dogoo.SystemWeighingSas.service.WeighingStationService;
import com.dogoo.SystemWeighingSas.unitity.response.Response;
import com.dogoo.SystemWeighingSas.unitity.response.ResponseFactory;
import com.dogoo.SystemWeighingSas.validator.WeighingStationValidator;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/o/dogoo/weighing-station")
public class WeighingStationController {

    private final WeighingStationService weighingStationService;
    private final WeighingStationValidator validator;
    private final CommonToken commonToken;

    public WeighingStationController(WeighingStationService weighingStationService,
                                     WeighingStationValidator validator,
                                     CommonToken commonToken) {
        this.weighingStationService = weighingStationService;
        this.validator = validator;
        this.commonToken = commonToken;
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
            httpServletResponse.setStatus(400);
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

            httpServletResponse.setStatus(400);
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

            httpServletResponse.setStatus(400);
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

    @GetMapping("/get-list-weighing-station")
    public Response getListWeighingStationByCustomer(HttpServletRequest request) {
        try {
            return ResponseFactory.getSuccessResponse(Response.SUCCESS,
                    weighingStationService.getWeighingStationByCustomer(getUserToken(request)));

        } catch (Exception exception) {
            return ResponseFactory.getClientErrorResponse(exception.getMessage());
        }
    }

    @GetMapping("/get-auto-code")
    public Response getAutoCodeCustomer(@RequestParam(name = "name", defaultValue = "", required = false) String name,
                                        HttpServletResponse httpServletResponse) {
        try {
            if (name.equals(""))
                return ResponseFactory.getSuccessResponse(Response.SUCCESS, "");

            return ResponseFactory.getSuccessResponse(Response.SUCCESS,
                    weighingStationService.getCodeWeighingStation(name));

        } catch (Exception exception) {
            httpServletResponse.setStatus(400);
            return ResponseFactory.getClientErrorResponse(exception.getMessage());
        }
    }

    @PutMapping("/change-status/{id}")
    public Response changeStatus(@PathVariable("id") long id,
                                 @RequestBody WeighingStationMapperModel model) {
        try {
            weighingStationService.changeStatus(id, model);
            return ResponseFactory.getSuccessResponse(Response.SUCCESS);
        } catch (Exception exception) {
            return ResponseFactory.getClientErrorResponse(exception.getMessage());
        }
    }

    private UserTokenModel getUserToken(HttpServletRequest request) {
        return commonToken.getUserToken(request);
    }
}
