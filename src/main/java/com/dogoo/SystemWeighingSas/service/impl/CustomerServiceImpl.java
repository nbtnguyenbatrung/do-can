package com.dogoo.SystemWeighingSas.service.impl;

import com.dogoo.SystemWeighingSas.config.Constants;
import com.dogoo.SystemWeighingSas.dao.ICustomerDao;
import com.dogoo.SystemWeighingSas.entity.Customer;
import com.dogoo.SystemWeighingSas.mapper.CustomerMapper;
import com.dogoo.SystemWeighingSas.model.CustomerMapperModel;
import com.dogoo.SystemWeighingSas.service.CustomerService;
import com.dogoo.SystemWeighingSas.unitity.response.ResultResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private ICustomerDao iCustomerDao;
    @Autowired
    private CustomerMapper mapper;

    @Override
    public Customer addCustomer(CustomerMapperModel model) {

        Customer to = Constants.SERIALIZER.convertValue(model, Customer.class);
        return iCustomerDao.save(to);
    }

    @Override
    public Customer updateCustomer(long id, CustomerMapperModel model) {
        Optional<Customer> optionalCustomer = iCustomerDao.findById(id);

        if (optionalCustomer.isPresent()) {
            Customer to = optionalCustomer.get();
            to.setCustomerName(model.getCustomerName());
            to.setEmail(model.getEmail());
            to.setPhoneNumber(model.getPhoneNumber());

            return iCustomerDao.save(to);
        }
        return new Customer();
    }

    @Override
    public void deleteCustomer(long id) {
        iCustomerDao.deleteById(id);
    }

    @Override
    public ResultResponse<CustomerMapperModel> getCustomers(Integer pageSize, Integer page) {

        Sort sort = Sort.by(Sort.Direction.DESC, "createDate");
        Pageable pageable = PageRequest.of(page, pageSize, sort);
        Page<Customer> customers = iCustomerDao.findAll(pageable);
        Long total = iCustomerDao.count();
        ResultResponse<CustomerMapperModel> resultResponse = new ResultResponse<>();
        resultResponse.setData(
                customers.getContent()
                        .stream()
                        .map(customer -> mapper.mapperModelFromEntry(customer))
                        .collect(Collectors.toList()));
        resultResponse.setLimit(pageSize);
        resultResponse.setPage(page);
        resultResponse.setTotal(total);
        resultResponse.setTotalPage((int) (total / pageSize));
        return resultResponse;
    }

    @Override
    public Customer getCustomer(long id) {

        Optional<Customer> optionalCustomer = iCustomerDao.findById(id);
        return optionalCustomer.orElse(null);
    }
}
