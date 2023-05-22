package com.dogoo.SystemWeighingSas.thread;

import com.dogoo.SystemWeighingSas.config.Constants;
import com.dogoo.SystemWeighingSas.dao.IWeightSlipDao;
import com.dogoo.SystemWeighingSas.entity.WeightSlip;
import com.dogoo.SystemWeighingSas.model.WeightSlipMapperModel;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.concurrent.Callable;

@Slf4j
public class JobCompare implements Callable<List<WeightSlip>> {
    private List<WeightSlipMapperModel> list;
    private final IWeightSlipDao iWeightSlipDao;
    private final String key;
    private final String databaseKey;

    public JobCompare(List<WeightSlipMapperModel> list,
                      IWeightSlipDao iWeightSlipDao,
                      String key,
                      String databaseKey) {
        this.list = list;
        this.iWeightSlipDao = iWeightSlipDao;
        this.key = key;
        this.databaseKey = databaseKey;
    }

    @Override
    public List<WeightSlip> call() throws Exception {

        list.forEach(model ->{
            WeightSlip to = Constants.SERIALIZER.convertValue(model, WeightSlip.class);
            to.setKey(key);
            to.setDatabaseKey(databaseKey);
            to.setAction(Constants.ACTION_CREATE);
            iWeightSlipDao.save(to);
        });
        return null;
    }
}
