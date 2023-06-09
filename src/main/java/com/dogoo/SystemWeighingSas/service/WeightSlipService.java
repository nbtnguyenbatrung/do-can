package com.dogoo.SystemWeighingSas.service;

import com.dogoo.SystemWeighingSas.entity.WeightSlip;
import com.dogoo.SystemWeighingSas.model.WeightSlipCriteria;
import com.dogoo.SystemWeighingSas.model.WeightSlipMapperModel;
import com.dogoo.SystemWeighingSas.unitity.response.ResultResponse;
import org.springframework.data.domain.Page;

import java.util.List;

public interface WeightSlipService {

    void SyncData(String key, String databaseKey, List<WeightSlipMapperModel> list);

    void SyncDataNew(String key, String databaseKey, List<WeightSlipMapperModel> list);

    void create(WeightSlipMapperModel model);

    List<WeightSlip> getWeightSlips();

    WeightSlip getWeightSlipsOrder();

    List<WeightSlip> getWeightSlipsAction();

    ResultResponse<WeightSlip> getWeightSlips(String weighingStationCode, Integer limit, Integer page);

    Page<WeightSlip> getWeightSlipsFilter(Integer limit,
                                          Integer page,
                                          WeightSlipCriteria weightSlipCriteria);

    List<String> findSoXeDistinct(String databaseKey);
    List<String> findTenHangDistinct(String databaseKey);
    List<String> findKhachHangDistinct(String databaseKey);

    void deleteWeightSlips(long weightSlipId);
}
