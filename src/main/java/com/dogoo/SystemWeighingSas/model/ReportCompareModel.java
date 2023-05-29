package com.dogoo.SystemWeighingSas.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ReportCompareModel {

    private long bill ;
    private long netWeight;
    private long revenue;

    private long billPercent ;
    private long netWeightPercent;
    private long revenuePercent;

}
