package com.dogoo.SystemWeighingSas.model;

import com.dogoo.SystemWeighingSas.entity.Role;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class AccountMapperModel {

    private Long accountId;

    private String name;

    private String phoneNumber;

    private String screenName;

    private String password;

    private String email;

    private String status;

    private String role;
    private Boolean roleCreate;
    private Boolean roleView;
    private Boolean roleAll;
    private String key;

    private Timestamp createDate;

    private List<RoleMapperModel> roleList;

    private String newPassword;
    private String confirmPassword;
}
