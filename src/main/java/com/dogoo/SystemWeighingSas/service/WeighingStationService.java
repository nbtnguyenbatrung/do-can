package com.dogoo.SystemWeighingSas.service;

import com.dogoo.SystemWeighingSas.entity.WeighingStation;
import com.dogoo.SystemWeighingSas.model.UserTokenModel;
import com.dogoo.SystemWeighingSas.model.WeighingStationMapperModel;
import com.dogoo.SystemWeighingSas.unitity.response.ResultResponse;

import java.util.List;

public interface WeighingStationService {

    WeighingStation addWeighingStation(WeighingStationMapperModel model);
    WeighingStation updateWeighingStation(long id, WeighingStationMapperModel model);
    void deleteWeighingStation(long id);
    ResultResponse<WeighingStationMapperModel> getWeighingStations(Integer pageSize, Integer page);
    WeighingStation getWeighingStation(long id);

    List<WeighingStation> getWeighingStationByCustomer(UserTokenModel model);
    void deleteWeighingStationByCustomer(long customerId);

    String getCodeWeighingStation(String name);

    List<WeighingStationMapperModel> getWeighingStationByCustomerId(long customerId);

    void changeStatus(long id , WeighingStationMapperModel model);

    List<WeighingStation> getWeighingStationByCustomerKey(String key);

}
