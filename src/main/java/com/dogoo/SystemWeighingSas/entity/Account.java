package com.dogoo.SystemWeighingSas.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
    private Integer accountId;

    private String name = "";

    private String phoneNumber = "";

    private String screenName = "";

    private String password = "";

    private String email = "";

    private String status = "";

    private String role = "";
    private Boolean roleCreate = Boolean.FALSE;
    private Boolean roleView = Boolean.FALSE;

    private String key = "";

    private Boolean changePassword = Boolean.FALSE;

    private Timestamp createDate = new Timestamp(System.currentTimeMillis());
}
