package com.dogoo.SystemWeighingSas.service.impl;

import com.dogoo.SystemWeighingSas.common.generalPassword.PwdGenerator;
import com.dogoo.SystemWeighingSas.config.Constants;
import com.dogoo.SystemWeighingSas.dao.ICustomerDao;
import com.dogoo.SystemWeighingSas.dao.IWeighingStationDao;
import com.dogoo.SystemWeighingSas.entity.Customer;
import com.dogoo.SystemWeighingSas.entity.WeighingStation;
import com.dogoo.SystemWeighingSas.mapper.WeighingStationMapper;
import com.dogoo.SystemWeighingSas.model.UserTokenModel;
import com.dogoo.SystemWeighingSas.model.WeighingStationMapperModel;
import com.dogoo.SystemWeighingSas.service.WeighingStationService;
import com.dogoo.SystemWeighingSas.unitity.response.ResultResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class WeighingStationServiceImpl implements WeighingStationService {

    @Autowired
    private IWeighingStationDao iWeighingStationDao;
    @Autowired
    private WeighingStationMapper mapper;
    @Autowired
    private ICustomerDao iCustomerDao;
    @Autowired
    private PwdGenerator pwdGenerator;

    @Override
    public WeighingStation addWeighingStation(WeighingStationMapperModel model) {
        WeighingStation to = Constants.SERIALIZER.convertValue(model, WeighingStation.class);
        return iWeighingStationDao.save(to);
    }

    @Override
    public WeighingStation updateWeighingStation(long id, WeighingStationMapperModel model) {
        Optional<WeighingStation> optionalWeighingStation = iWeighingStationDao.findById(id);

        if (optionalWeighingStation.isPresent()) {
            WeighingStation to = optionalWeighingStation.get();
            to.setWeighingStationName(model.getWeighingStationName());
            to.setAddress(model.getAddress());
            to.setCustomerId(model.getCustomerId());
            to.setStatus(model.getStatus());

            return iWeighingStationDao.save(to);
        }
        return new WeighingStation();
    }

    @Override
    public void deleteWeighingStation(long id) {
        iWeighingStationDao.deleteById(id);
    }

    @Override
    public ResultResponse<WeighingStationMapperModel> getWeighingStations(Integer pageSize, Integer page) {
        Sort sort = Sort.by(Sort.Direction.DESC, "createDate");
        Pageable pageable = PageRequest.of(page, pageSize, sort);
        Page<WeighingStation> weighingStations = iWeighingStationDao.findAll(pageable);
        Long total = iWeighingStationDao.count();
        ResultResponse<WeighingStationMapperModel> resultResponse = new ResultResponse<>();
        resultResponse.setData(
                weighingStations.getContent()
                        .stream()
                        .map(weighingStation -> mapper.mapperModelFromEntry(weighingStation))
                        .collect(Collectors.toList()));
        resultResponse.setLimit(pageSize);
        resultResponse.setPage(page);
        resultResponse.setTotal(total);
        resultResponse.setTotalPage((int) (total / pageSize));
        return resultResponse;
    }

    @Override
    public WeighingStation getWeighingStation(long id) {

        Optional<WeighingStation> optionalWeighingStation = iWeighingStationDao.findById(id);
        return optionalWeighingStation.orElse(null);
    }

    @Override
    public List<WeighingStation> getWeighingStationByCustomer(UserTokenModel model) {

        Customer customer = iCustomerDao.findCustomerByKey(model.getKey());
        return iWeighingStationDao.findWeighingStationByCustomerId(customer.getId());
    }

    @Override
    public void deleteWeighingStationByCustomer(long customerId) {
        iWeighingStationDao.deleteAllByCustomerId(customerId);
    }

    @Override
    public String getCodeWeighingStation(String name) {
        return pwdGenerator.getCodeWeighingStation(name);
    }

    @Override
    public List<WeighingStationMapperModel> getWeighingStationByCustomerId(long customerId) {
        return iWeighingStationDao.findWeighingStationByCustomerId(customerId)
                .stream()
                .map(weighingStation -> {
                    return Constants.SERIALIZER.convertValue(
                            weighingStation, WeighingStationMapperModel.class);
                })
                .collect(Collectors.toList());
    }
}
