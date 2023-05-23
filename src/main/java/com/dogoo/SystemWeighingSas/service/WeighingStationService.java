package com.dogoo.SystemWeighingSas.service;

import com.dogoo.SystemWeighingSas.entity.WeighingStation;
import com.dogoo.SystemWeighingSas.model.WeighingStationMapperModel;
import com.dogoo.SystemWeighingSas.unitity.response.ResultResponse;

public interface WeighingStationService {

    WeighingStation addWeighingStation(WeighingStationMapperModel model);
    WeighingStation updateWeighingStation(long id, WeighingStationMapperModel model);
    void deleteWeighingStation(long id);
    ResultResponse<WeighingStationMapperModel> getWeighingStations(Integer pageSize, Integer page);
    WeighingStation getWeighingStation(long id);

}
