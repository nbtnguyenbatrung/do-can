package com.dogoo.SystemWeighingSas.service;

import com.dogoo.SystemWeighingSas.entity.DatabaseKey;
import com.dogoo.SystemWeighingSas.model.DatabaseKeyMapperModel;
import com.dogoo.SystemWeighingSas.model.UserTokenModel;
import com.dogoo.SystemWeighingSas.unitity.response.ResultResponse;

public interface DatabaseKeyService {
    void createDatabaseKey(DatabaseKeyMapperModel databaseKeyMapperModel);
    ResultResponse<DatabaseKey> getDatabaseKeyByKey(
            UserTokenModel model, Integer limit, Integer page);

}
