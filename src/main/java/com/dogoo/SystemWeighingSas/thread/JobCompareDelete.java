package com.dogoo.SystemWeighingSas.thread;

import com.dogoo.SystemWeighingSas.dao.IWeightSlipDao;
import com.dogoo.SystemWeighingSas.entity.WeightSlip;
import com.dogoo.SystemWeighingSas.model.WeightSlipMapperModel;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.concurrent.Callable;

@Slf4j
public class JobCompareDelete implements Callable<List<WeightSlip>> {
    private List<WeightSlip> list;
    private final IWeightSlipDao iWeightSlipDao;


    public JobCompareDelete(List<WeightSlip> list,
                            IWeightSlipDao iWeightSlipDao) {
        this.list = list;
        this.iWeightSlipDao = iWeightSlipDao;
    }

    @Override
    public List<WeightSlip> call() throws Exception {
        list.forEach(model -> iWeightSlipDao.deleteWeightSlipByMaPhieu(model.getMaPhieu()));
        return null;
    }
}
