package com.dogoo.SystemWeighingSas.model;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.List;

public class WeightSlipCriteria {

    private String databaseKey;
    private List<String> soXe;
    private List<String> tenHang;
    private List<String> khachHang;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime startDate;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime endDate;
    private String search;

    public String getDatabaseKey() {
        return databaseKey;
    }

    public void setDatabaseKey(String databaseKey) {
        this.databaseKey = databaseKey;
    }

    public List<String> getSoXe() {
        return soXe;
    }

    public void setSoXe(List<String> soXe) {
        this.soXe = soXe;
    }

    public List<String> getTenHang() {
        return tenHang;
    }

    public void setTenHang(List<String> tenHang) {
        this.tenHang = tenHang;
    }

    public List<String> getKhachHang() {
        return khachHang;
    }

    public void setKhachHang(List<String> khachHang) {
        this.khachHang = khachHang;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }
}
