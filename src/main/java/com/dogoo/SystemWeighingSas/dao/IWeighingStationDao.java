package com.dogoo.SystemWeighingSas.dao;

import com.dogoo.SystemWeighingSas.entity.WeighingStation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface IWeighingStationDao extends JpaRepository<WeighingStation,Long> {

    long countByCustomerId(long customerId);

    List<WeighingStation> findWeighingStationByCustomerId(long customerId);

    @Query("select ac from DG_WeighingStation ac Where ac.customerId=:customerId AND weighingStationCode=:weighingStationCode ")
    WeighingStation getWeighingStation(@Param("customerId") long customerId,
                                       @Param("weighingStationCode") String weighingStationCode);

    void deleteAllByCustomerId(long customerId);

    boolean existsByWeighingStationCode(String weighingStationCode);

    WeighingStation findById(long id);

}
