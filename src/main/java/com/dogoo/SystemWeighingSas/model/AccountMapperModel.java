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
public class AccountMapperModel {

    private Integer accountId;

    private String name;

    private String phoneNumber;

    private String screenName;

    private String password;

    private String email;

    private String status;

    private String role;
    private Boolean roleCreate;
    private Boolean roleView;
    private String key;

    private Timestamp createDate;
}
