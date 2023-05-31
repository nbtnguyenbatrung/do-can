package com.dogoo.SystemWeighingSas.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@Entity(name = "DG_WeightSlip")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class WeightSlip {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long weightSlipId;

    
    private String key = "";
    
    private String databaseKey = "";
    
    private String action = "";
    
    private String maPhieu = "";
    
    private String khachHang = "";
    
    private String tenHang = "";
    
    private String maHang = "";
    
    private String maKH = "";
    
    private String soXe = "";

    @Column(name = "kl_co_tai")
    private long coTai;
    @Column(name = "kl_k_tai")
    private long tareWeight;
    @Column(name = "kl_hang")
    private long hang;
    private Timestamp ngayCan;
//    @Column(columnDefinition = "TIMESTAMP")
//    private LocalDateTime ngayCan;
    private Timestamp gioCoTai;
    private Timestamp gioKTai;
    
    private String ghiChu = "";
    
    private String xN = "";

    private String urlCoTai1 = "";
    
    private String urlCoTai2 = "";
    
    private String urlKoTai1 = "";
    
    private String urlKoTai2 = "";
    
    private String chietKhau = "";
    
    private String donGia = "";
    
    private String sauCK = "";
    private Long thanhTien;

}
