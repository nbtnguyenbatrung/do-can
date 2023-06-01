package com.dogoo.SystemWeighingSas.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ChartMapperModel {

    private String name;

    private List<DataMapperModel> list;

    private List<String> nameXAxis = new ArrayList<>();
    private List<Long> currentPeriod = new ArrayList<>();
    private List<Long> samePeriod = new ArrayList<>();
    private long jump;
}
