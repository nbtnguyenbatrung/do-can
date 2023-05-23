package com.dogoo.SystemWeighingSas.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class CustomerMapperModel {

    private Long id;

    private String customerName = "";
    private String customerCode = "";
    private String key = "";
    private String email = "";
    private String phoneNumber = "";

    private String weighingStations ;
    private long  numberWeighingStations ;
    private long numberAccount ;
    private Timestamp createDate;
}
