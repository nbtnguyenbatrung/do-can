package com.dogoo.SystemWeighingSas.service;

import com.dogoo.SystemWeighingSas.entity.Account;
import com.dogoo.SystemWeighingSas.entity.WeightSlip;
import com.dogoo.SystemWeighingSas.model.WeightSlipMapperModel;

import java.util.List;

public interface WeightSlipService {

    void SyncData( String key, String databaseKey, List<WeightSlipMapperModel> list);
    void SyncDataNew( String key, String databaseKey, List<WeightSlipMapperModel> list);
    void create(WeightSlipMapperModel model);
    List<WeightSlip> getWeightSlips();

    WeightSlip getWeightSlipsOrder();

    List<WeightSlip> getWeightSlipsAction();
}
