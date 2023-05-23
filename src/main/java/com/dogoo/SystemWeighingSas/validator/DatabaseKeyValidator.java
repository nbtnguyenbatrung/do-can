package com.dogoo.SystemWeighingSas.validator;

import com.dogoo.SystemWeighingSas.dao.ICustomerDao;
import com.dogoo.SystemWeighingSas.dao.IWeighingStationDao;
import com.dogoo.SystemWeighingSas.entity.Customer;
import com.dogoo.SystemWeighingSas.entity.WeighingStation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class DatabaseKeyValidator {

    @Autowired
    private ICustomerDao iCustomerDao;
    @Autowired
    private IWeighingStationDao iWeighingStationDao;

    public ResponseEntity<String> validatorNotFoundKey(Customer customer){

        if (customer != null)
            return  null;

        return new ResponseEntity<>("Không tồn tại key khách hàng trong hệ thống", HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<String> validatorNotFoundDatabaseKey(Customer customer,
                                                               String databaseKey){

        WeighingStation weighingStation = iWeighingStationDao.getWeighingStation(customer.getId(), databaseKey);

        if (weighingStation != null)
            return  null;

        return new ResponseEntity<>("Không tồn tại mã trạm cân cùng với key khách hàng trong hệ thống", HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<String> validatorCheck(String key, String databaseKey){
        Customer customer = iCustomerDao.findCustomerByKey(key);
        ResponseEntity<String> responseKey = validatorNotFoundKey(customer);
        ResponseEntity<String> responseDatabaseKey = validatorNotFoundDatabaseKey(customer, databaseKey);

        if (responseKey != null)
            return  responseKey;

        return responseDatabaseKey;
    }
}
