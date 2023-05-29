package com.dogoo.SystemWeighingSas.service.impl;

import com.dogoo.SystemWeighingSas.config.Constants;
import com.dogoo.SystemWeighingSas.dao.IWeightSlipDao;
import com.dogoo.SystemWeighingSas.entity.Account;
import com.dogoo.SystemWeighingSas.entity.WeightSlip;
import com.dogoo.SystemWeighingSas.mapper.WeightMapper;
import com.dogoo.SystemWeighingSas.model.WeightSlipMapperModel;
import com.dogoo.SystemWeighingSas.service.WeightSlipService;
import com.dogoo.SystemWeighingSas.thread.JobCompare;
import com.dogoo.SystemWeighingSas.thread.JobCompareDelete;
import com.dogoo.SystemWeighingSas.thread.JobCompareUpdate;
import com.dogoo.SystemWeighingSas.unitity.response.ResultResponse;
import com.dogoo.SystemWeighingSas.unitity.tasks.ThreadPool;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.stream.Collectors;

@Service
@Transactional
public class WeightSlipServiceImpl implements WeightSlipService {

    private final IWeightSlipDao iWeightSlipDao;
    private final ExecutorService executor;
    private final WeightMapper mapper;

    public WeightSlipServiceImpl(IWeightSlipDao iWeightSlipDao, WeightMapper mapper) {
        this.iWeightSlipDao = iWeightSlipDao;
        this.mapper = mapper;
        executor = ThreadPool.builder()
                .setCoreSize(5)
                .setQueueSize(0)
                .setNamePrefix("processing compare")
                .setDaemon(true)
                .build();
    }

    @Override
    public void SyncData(String key, String databaseKey, List<WeightSlipMapperModel> list) {

        long startCurrent = System.currentTimeMillis();
        Integer count = iWeightSlipDao.getCountWeightSlip();
        if (count > 0) {
            syncDataWeightSlipNewAndOld(key, databaseKey, list);
        } else {
            syncDataWeightSlipNew(key, databaseKey, list);
        }
        long endCurrent = System.currentTimeMillis();
        System.out.println("finish " + (endCurrent-startCurrent));
    }

    @Override
    public void SyncDataNew(String key, String databaseKey, List<WeightSlipMapperModel> list) {
        int size = Constants.size;
        int i = 0;
        int sizeList = list.size();
        while (true) {

            int toIndex = Math.min(i*size+size , sizeList);
            List<WeightSlipMapperModel> list1 = list.subList(i*size, toIndex );

            JobCompareUpdate jobCompare = new JobCompareUpdate(list1, iWeightSlipDao, key, databaseKey, mapper);
            Constants.jobSubmit.add(executor.submit(jobCompare));
            i++;
            if (toIndex == sizeList){
                return;
            }
        }
    }

    @Override
    public void create(WeightSlipMapperModel model) {
        WeightSlip to = Constants.SERIALIZER.convertValue(model, WeightSlip.class);
        iWeightSlipDao.save(to);
    }

    @Override
    public List<WeightSlip> getWeightSlips() {
        return iWeightSlipDao.findAll();
    }

    @Override
    public WeightSlip getWeightSlipsOrder() {
        List<WeightSlip> list1 = iWeightSlipDao.getAllWeightSlipOrder();

        if (list1.isEmpty())
            return new WeightSlip();

        return list1.get(0);
    }

    @Override
    public List<WeightSlip> getWeightSlipsAction() {
        return iWeightSlipDao.getAllWeightSlipAction();
    }

    @Override
    public ResultResponse<WeightSlip> getWeightSlips(String weighingStationCode,
                                                     Integer limit,
                                                     Integer page) {
        Sort sort = Sort.by(Sort.Direction.DESC, "ngayCan");
        Pageable pageable = PageRequest.of(page, 30, sort);
        Page<WeightSlip> weightSlips = iWeightSlipDao
                .findAllByDatabaseKey( weighingStationCode, pageable);
        long total = iWeightSlipDao.countByDatabaseKey(weighingStationCode);
        ResultResponse<WeightSlip> resultResponse = new ResultResponse<>();
        resultResponse.setData(weightSlips.getContent());
        resultResponse.setLimit(limit);
        resultResponse.setPage(page);
        resultResponse.setTotal(total);
        resultResponse.setTotalPage((int) (total / limit));
        return resultResponse;
    }

    private void syncDataWeightSlipNew(String key,
                                       String databaseKey,
                                       List<WeightSlipMapperModel> list) {

        int size = Constants.size;
        int i = 0;
        int sizeList = list.size();
        while (true) {

            int toIndex = Math.min(i*size+size , sizeList);
            List<WeightSlipMapperModel> list1 = list.subList(i*size, toIndex );

            JobCompare jobCompare = new JobCompare(list1, iWeightSlipDao, key, databaseKey);
            Constants.jobSubmit.add(executor.submit(jobCompare));
            i++;
            if (toIndex == sizeList){
                return;
            }
        }

    }

    private void syncDataWeightSlipNewAndOld(String key,
                                             String databaseKey,
                                             List<WeightSlipMapperModel> list) {

        List<WeightSlip> weightSlipList = iWeightSlipDao.getAllWeightSlipByKeyAndDatabase(key, databaseKey);
        List<WeightSlip> list1 = weightSlipList.stream()
                .filter(weightSlip -> list.stream().map(WeightSlipMapperModel::getMaPhieu)
                        .noneMatch(weightSlip.getMaPhieu()::equals))
                .collect(Collectors.toList());

        deleteWeightSlip(list1);
        compareWeightSlip(key, databaseKey, list);

    }

    private void deleteWeightSlip(List<WeightSlip> list){

        int size = Constants.size;
        int i = 0;
        int sizeList = list.size();
        while (true) {

            int toIndex = Math.min(i*size+size , sizeList);
            List<WeightSlip> list1 = list.subList(i*size, toIndex );

            JobCompareDelete jobCompare = new JobCompareDelete(list1, iWeightSlipDao);
            Constants.jobSubmit.add(executor.submit(jobCompare));
            i++;
            if (toIndex == sizeList){
                return;
            }
        }

    }

    private void compareWeightSlip( String key,
                                    String databaseKey,
                                    List<WeightSlipMapperModel> list){

        int size = Constants.size;
        int i = 0;
        int sizeList = list.size();
        while (true) {

            int toIndex = Math.min(i*size+size , sizeList);
            List<WeightSlipMapperModel> list1 = list.subList(i*size, toIndex );

            JobCompareUpdate jobCompare = new JobCompareUpdate(list1, iWeightSlipDao, key, databaseKey, mapper);
            Constants.jobSubmit.add(executor.submit(jobCompare));
            i++;
            if (toIndex == sizeList){
                return;
            }
        }

    }
}
