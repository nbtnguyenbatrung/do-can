package com.dogoo.SystemWeighingSas.validator;

import com.dogoo.SystemWeighingSas.dao.IWeighingStationDao;
import com.dogoo.SystemWeighingSas.entity.WeighingStation;
import com.dogoo.SystemWeighingSas.unitity.response.Response;
import com.dogoo.SystemWeighingSas.unitity.response.ResponseFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class WeighingStationValidator {

    @Autowired
    private IWeighingStationDao iWeighingStationDao;

    public Response validatorExits (long id) {

        WeighingStation weighingStation = iWeighingStationDao.findById(id);
        if (weighingStation != null)
            return null;

        return ResponseFactory.getClientErrorResponse("Không tồn tại Trạm cân");
    }
}
