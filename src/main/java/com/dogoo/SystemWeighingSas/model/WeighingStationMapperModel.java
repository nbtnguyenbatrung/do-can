package com.dogoo.SystemWeighingSas.model;

import com.dogoo.SystemWeighingSas.enumEntity.StatusEnum;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class WeighingStationMapperModel {

    private Long id;

    private String weighingStationName ;
    private String weighingStationCode ;
    private String address ;
    private long customerId;

    @Enumerated(EnumType.STRING)
    private StatusEnum status = StatusEnum.active;

    private String customerName;
    private String phoneNumber;

    private Timestamp createDate;

}
