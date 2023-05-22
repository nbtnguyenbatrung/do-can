package com.dogoo.SystemWeighingSas.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class WeightSlipMapperModel {

    private Long weightSlipId;
    private String key;
    private String databaseKey;
    private String action;
    private String maPhieu;
    private String khachHang;
    private String tenHang;
    private String maHang;
    private String maKH;
    private String soXe;

    @JsonProperty("coTai")
    private long coTai;

    @JsonProperty("tareWeight")
    private long tareWeight;

    @JsonProperty("hang")
    private long hang;

    private Timestamp ngayCan;
    private Timestamp gioCoTai;
    private Timestamp gioKTai;
    private String ghiChu;

    @JsonProperty("xn")
    private String xN;
    private String urlCoTai1;
    private String urlCoTai2;
    private String urlKoTai1;
    private String urlKoTai2;
    private String chietKhau;
    private String donGia;
    private String sauCK;
    private Long thanhTien;

}
