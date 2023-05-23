package com.dogoo.SystemWeighingSas.mapper;

import com.dogoo.SystemWeighingSas.config.Constants;
import com.dogoo.SystemWeighingSas.dao.IAccountDao;
import com.dogoo.SystemWeighingSas.dao.IWeighingStationDao;
import com.dogoo.SystemWeighingSas.entity.Customer;
import com.dogoo.SystemWeighingSas.entity.WeighingStation;
import com.dogoo.SystemWeighingSas.model.CustomerMapperModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerMapper {

    @Autowired
    private IWeighingStationDao iWeighingStationDao;
    @Autowired
    private IAccountDao iAccountDao;

    public CustomerMapperModel mapperModelFromEntry(Customer from) {
        CustomerMapperModel to = Constants.SERIALIZER.convertValue(from, CustomerMapperModel.class);

        to.setNumberWeighingStations(iWeighingStationDao.countByCustomerId(from.getId()));
        to.setNumberAccount(iAccountDao.countByKey(from.getKey()));
        List<WeighingStation> list = iWeighingStationDao.findWeighingStationByCustomerId(from.getId());

        String s = list.stream()
                .map(WeighingStation::getWeighingStationName)
                .collect(Collectors.joining(", "));
        to.setWeighingStations(s);

        return to;
    }

}
