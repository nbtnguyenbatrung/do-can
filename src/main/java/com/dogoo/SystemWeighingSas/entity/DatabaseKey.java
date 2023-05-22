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

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@Entity(name = "DG_DatabaseKey")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DatabaseKey {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer databaseKeyId;

    private String key;
    private String databaseKey;
}
