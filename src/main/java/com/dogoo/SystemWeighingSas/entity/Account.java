package com.dogoo.SystemWeighingSas.entity;

import com.dogoo.SystemWeighingSas.enumEntity.RoleEnum;
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
@Entity(name = "DG_account")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long accountId;

    private String name = "";

    private String phoneNumber = "";

    private String screenName = "";

    private String password = "";

    private String email = "";

    @Enumerated(EnumType.STRING)
    private StatusEnum status = StatusEnum.active;

    @Enumerated(EnumType.STRING)
    private RoleEnum role = RoleEnum.user;
    private Boolean roleCreate = Boolean.FALSE;
    private Boolean roleView = Boolean.FALSE;
    private Boolean roleAll = Boolean.TRUE;

    private String key = "";

    private Boolean changePassword = Boolean.FALSE;

    private Timestamp createDate = new Timestamp(System.currentTimeMillis());
}
