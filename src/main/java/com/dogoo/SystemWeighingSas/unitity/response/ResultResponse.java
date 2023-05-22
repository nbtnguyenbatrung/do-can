package com.dogoo.SystemWeighingSas.unitity.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tailetuan on 09/12/2020
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class ResultResponse<T> {
    private List<T> data = new ArrayList<>();
    private Integer limit;
    private Integer page;
    private Integer totalPage;
    private Long total;

    public static <T> ResultResponse<T> empty() {
        return new ResultResponse<T>();
    }

}
