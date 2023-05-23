package com.dogoo.SystemWeighingSas.controller;

import com.dogoo.SystemWeighingSas.entity.Customer;
import com.dogoo.SystemWeighingSas.model.CustomerMapperModel;
import com.dogoo.SystemWeighingSas.service.CustomerService;
import com.dogoo.SystemWeighingSas.unitity.response.Response;
import com.dogoo.SystemWeighingSas.unitity.response.ResponseFactory;
import com.dogoo.SystemWeighingSas.validator.CustomerValidator;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/o/dogoo/customer")
public class CustomerController {

    private final CustomerService customerService;
    private final CustomerValidator validator;

    public CustomerController(CustomerService customerService,
                              CustomerValidator validator) {
        this.customerService = customerService;
        this.validator = validator;
    }

    @PostMapping("/add")
    public Response addCustomer(@RequestBody CustomerMapperModel model,
                                HttpServletResponse httpServletResponse) {
        try {
            Response response = validator.validatorAdd(model);
            if (response == null) {
                Customer to = customerService.addCustomer(model);
                return ResponseFactory.getSuccessResponse(Response.SUCCESS, to);
            }

            httpServletResponse.setStatus(404);
            return response;
        } catch (Exception exception) {
            return ResponseFactory.getClientErrorResponse(exception.getMessage());
        }
    }

    @PutMapping("/update/{id}")
    public Response updateCustomer(@PathVariable("id") long id,
                                   @RequestBody CustomerMapperModel model,
                                   HttpServletResponse httpServletResponse) {
        try {
            Response response = validator.validatorUpdate(id, model);
            if (response == null) {
                Customer to = customerService.updateCustomer(id, model);
                return ResponseFactory.getSuccessResponse(Response.SUCCESS, to);
            }

            httpServletResponse.setStatus(404);
            return response;
        } catch (Exception exception) {
            return ResponseFactory.getClientErrorResponse(exception.getMessage());
        }
    }

    @DeleteMapping("/delete/{id}")
    public Response deleteCustomer(@PathVariable("id") long id,
                                   HttpServletResponse httpServletResponse) {
        try {
            Response response = validator.validatorExits(id);
            if (response == null) {
                customerService.deleteCustomer(id);
                return ResponseFactory.getSuccessResponse(Response.SUCCESS);
            }

            httpServletResponse.setStatus(404);
            return response;
        } catch (Exception exception) {
            return ResponseFactory.getClientErrorResponse(exception.getMessage());
        }
    }

    @GetMapping("/get/{id}")
    public Response getCustomer(@PathVariable("id") long id,
                                HttpServletResponse httpServletResponse) {
        try {
            Response response = validator.validatorExits(id);
            if (response == null) {
                Customer to = customerService.getCustomer(id);
                return ResponseFactory.getSuccessResponse(Response.SUCCESS, to);
            }

            httpServletResponse.setStatus(404);
            return response;
        } catch (Exception exception) {
            return ResponseFactory.getClientErrorResponse(exception.getMessage());
        }
    }

    @GetMapping("/get-list")
    public Response getListCustomer(@RequestParam(name = "pageSize", defaultValue = "10", required = false) Integer pageSize,
                                   @RequestParam(name = "page", defaultValue = "0", required = false) Integer page) {
        try {
            return ResponseFactory.getSuccessResponse(Response.SUCCESS,
                    customerService.getCustomers(pageSize, page));

        } catch (Exception exception) {
            return ResponseFactory.getClientErrorResponse(exception.getMessage());
        }
    }
}
