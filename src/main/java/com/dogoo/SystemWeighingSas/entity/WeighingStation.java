package com.dogoo.SystemWeighingSas.entity;

import com.dogoo.SystemWeighingSas.enumEntity.StatusEnum;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@Entity(name = "DG_WeighingStation")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class WeighingStation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String weighingStationName = "";
    private String weighingStationCode = "";
    private String address = "";
    private long customerId;

    @Enumerated(EnumType.STRING)
    private StatusEnum status = StatusEnum.active;

    private Timestamp createDate = new Timestamp(System.currentTimeMillis());
}
