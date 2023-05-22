package com.dogoo.SystemWeighingSas.service.impl;

import com.dogoo.SystemWeighingSas.config.Constants;
import com.dogoo.SystemWeighingSas.dao.IDatabaseKeyDao;
import com.dogoo.SystemWeighingSas.entity.DatabaseKey;
import com.dogoo.SystemWeighingSas.model.DatabaseKeyMapperModel;
import com.dogoo.SystemWeighingSas.model.UserTokenModel;
import com.dogoo.SystemWeighingSas.service.DatabaseKeyService;
import com.dogoo.SystemWeighingSas.unitity.response.ResultResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class DatabaseKeyServiceImpl implements DatabaseKeyService {

    private final IDatabaseKeyDao iDatabaseKeyDao;

    public DatabaseKeyServiceImpl(IDatabaseKeyDao iDatabaseKeyDao) {
        this.iDatabaseKeyDao = iDatabaseKeyDao;
    }

    @Override
    public void createDatabaseKey(DatabaseKeyMapperModel databaseKeyMapperModel) {

        DatabaseKey databaseKeyFound = iDatabaseKeyDao.fetchDatabaseKeyByKey(
                databaseKeyMapperModel.getKey(), databaseKeyMapperModel.getDatabaseKey());
        if (databaseKeyFound == null){
            DatabaseKey databaseKey = Constants.SERIALIZER.convertValue(
                    databaseKeyMapperModel, DatabaseKey.class);
            iDatabaseKeyDao.save(databaseKey);
        }
    }

    @Override
    public ResultResponse<DatabaseKey> getDatabaseKeyByKey(UserTokenModel model,
                                                           Integer limit,
                                                           Integer page) {
        Pageable pageable = PageRequest.of(page, 30);
        Page<DatabaseKey> databaseKeys = iDatabaseKeyDao.findAll(pageable);
        if (!model.getKey().equals("")){
            databaseKeys = iDatabaseKeyDao.findDatabaseKeyByKey(model.getKey(), pageable);
        }
        Long total = databaseKeys.getTotalElements();
        ResultResponse<DatabaseKey> resultResponse = new ResultResponse<>();
        resultResponse.setData(databaseKeys.getContent());
        resultResponse.setLimit(limit);
        resultResponse.setPage(page);
        resultResponse.setTotal(total);
        resultResponse.setTotalPage((int) (total / limit));
        return resultResponse;
    }
}
