package com.dogoo.SystemWeighingSas.service;

import com.dogoo.SystemWeighingSas.entity.Customer;
import com.dogoo.SystemWeighingSas.model.CustomerMapperModel;
import com.dogoo.SystemWeighingSas.unitity.response.ResultResponse;

public interface CustomerService {

    Customer addCustomer(CustomerMapperModel model);
    Customer updateCustomer(long id, CustomerMapperModel model);
    void deleteCustomer(long id);
    ResultResponse<CustomerMapperModel> getCustomers(Integer pageSize, Integer page);
    Customer getCustomer(long id);
}
