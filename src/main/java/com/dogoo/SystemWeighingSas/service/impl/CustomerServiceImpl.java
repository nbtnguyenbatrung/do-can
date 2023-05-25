package com.dogoo.SystemWeighingSas.service.impl;

import com.dogoo.SystemWeighingSas.common.generalPassword.PwdGenerator;
import com.dogoo.SystemWeighingSas.config.Constants;
import com.dogoo.SystemWeighingSas.dao.ICustomerDao;
import com.dogoo.SystemWeighingSas.entity.Customer;
import com.dogoo.SystemWeighingSas.mapper.CustomerMapper;
import com.dogoo.SystemWeighingSas.model.CustomerMapperModel;
import com.dogoo.SystemWeighingSas.model.WeighingStationMapperModel;
import com.dogoo.SystemWeighingSas.service.AccountService;
import com.dogoo.SystemWeighingSas.service.CustomerService;
import com.dogoo.SystemWeighingSas.service.WeighingStationService;
import com.dogoo.SystemWeighingSas.unitity.response.ResultResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private ICustomerDao iCustomerDao;
    @Autowired
    private CustomerMapper mapper;
    @Autowired
    private AccountService accountService;
    @Autowired
    private WeighingStationService weighingStationService;
    @Autowired
    private PwdGenerator pwdGenerator;

    @Override
    public Customer addCustomer(CustomerMapperModel model) throws MessagingException {

        Customer to = Constants.SERIALIZER.convertValue(model, Customer.class);
        accountService.createAccountCustomer(to);
        to = iCustomerDao.save(to);

        addListWeighingStation(to.getId(), model.getList());
        return to;
    }

    @Override
    public Customer updateCustomer(long id, CustomerMapperModel model) {
        Optional<Customer> optionalCustomer = iCustomerDao.findById(id);

        if (optionalCustomer.isPresent()) {
            Customer to = optionalCustomer.get();
            to.setCustomerName(model.getCustomerName());
            to.setEmail(model.getEmail());
            to.setPhoneNumber(model.getPhoneNumber());
            updateListWeighingStation(id, model.getList());
            return iCustomerDao.save(to);
        }
        return new Customer();
    }

    @Override
    public void deleteCustomer(long id) {
        weighingStationService.deleteWeighingStationByCustomer(id);
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

    @Override
    public String getCodeCustomer(String name) {
        return pwdGenerator.getCustomerCode(name);
    }

    private void addListWeighingStation(long customerId,
                                        List<WeighingStationMapperModel> list) {
        list.forEach(weighingStationMapperModel -> {
            weighingStationMapperModel.setCustomerId(customerId);
            weighingStationService.addWeighingStation(weighingStationMapperModel);
        });
    }

    private void updateListWeighingStation(long customerId,
                                           List<WeighingStationMapperModel> list) {
        list.forEach(weighingStationMapperModel -> {
            if (weighingStationMapperModel.getId() == 0){
                weighingStationMapperModel.setCustomerId(customerId);
                weighingStationService.addWeighingStation(weighingStationMapperModel);
            }else{
                weighingStationService.updateWeighingStation(weighingStationMapperModel.getId(),
                        weighingStationMapperModel);
            }
        });
    }
}
