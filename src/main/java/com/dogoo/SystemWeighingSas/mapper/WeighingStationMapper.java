package com.dogoo.SystemWeighingSas.mapper;

import com.dogoo.SystemWeighingSas.config.Constants;
import com.dogoo.SystemWeighingSas.dao.ICustomerDao;
import com.dogoo.SystemWeighingSas.entity.Customer;
import com.dogoo.SystemWeighingSas.entity.WeighingStation;
import com.dogoo.SystemWeighingSas.model.WeighingStationMapperModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class WeighingStationMapper {

    @Autowired
    private ICustomerDao iCustomerDao;

    public WeighingStationMapperModel mapperModelFromEntry(WeighingStation from){
        WeighingStationMapperModel to =
                Constants.SERIALIZER.convertValue(from, WeighingStationMapperModel.class);

        Optional<Customer> optionalCustomer = iCustomerDao.findById(from.getCustomerId());

        if (optionalCustomer.isPresent()){
            to.setCustomerName(optionalCustomer.get().getCustomerName());
            to.setPhoneNumber(optionalCustomer.get().getPhoneNumber());
        }

        return to;
    }
}
