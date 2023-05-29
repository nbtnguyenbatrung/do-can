package com.dogoo.SystemWeighingSas.model;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public class WeightSlipDto {

    private String maPhieu;
    private String khachHang;
    private String tenHang;
    private String maHang;
    private String maKH;
    private String soXe;
    private long coTai;
    private long tareWeight;
    private long hang;
    private LocalDateTime ngayCan;
    private Timestamp gioCoTai;
    private Timestamp gioKTai;
    private String ghiChu;
    private String xN;
    private String urlCoTai1;
    private String urlCoTai2;
    private String urlKoTai1;
    private String urlKoTai2;
    private String chietKhau;
    private String donGia;
    private String sauCK;
    private Long thanhTien;

    public WeightSlipDto() {
    }

    public String getMaPhieu() {
        return maPhieu;
    }

    public void setMaPhieu(String maPhieu) {
        this.maPhieu = maPhieu;
    }

    public String getKhachHang() {
        return khachHang;
    }

    public void setKhachHang(String khachHang) {
        this.khachHang = khachHang;
    }

    public String getTenHang() {
        return tenHang;
    }

    public void setTenHang(String tenHang) {
        this.tenHang = tenHang;
    }

    public String getMaHang() {
        return maHang;
    }

    public void setMaHang(String maHang) {
        this.maHang = maHang;
    }

    public String getMaKH() {
        return maKH;
    }

    public void setMaKH(String maKH) {
        this.maKH = maKH;
    }

    public String getSoXe() {
        return soXe;
    }

    public void setSoXe(String soXe) {
        this.soXe = soXe;
    }

    public long getCoTai() {
        return coTai;
    }

    public void setCoTai(long coTai) {
        this.coTai = coTai;
    }

    public long getTareWeight() {
        return tareWeight;
    }

    public void setTareWeight(long tareWeight) {
        this.tareWeight = tareWeight;
    }

    public long getHang() {
        return hang;
    }

    public void setHang(long hang) {
        this.hang = hang;
    }

    public LocalDateTime getNgayCan() {
        return ngayCan;
    }

    public void setNgayCan(LocalDateTime ngayCan) {
        this.ngayCan = ngayCan;
    }

    public Timestamp getGioCoTai() {
        return gioCoTai;
    }

    public void setGioCoTai(Timestamp gioCoTai) {
        this.gioCoTai = gioCoTai;
    }

    public Timestamp getGioKTai() {
        return gioKTai;
    }

    public void setGioKTai(Timestamp gioKTai) {
        this.gioKTai = gioKTai;
    }

    public String getGhiChu() {
        return ghiChu;
    }

    public void setGhiChu(String ghiChu) {
        this.ghiChu = ghiChu;
    }

    public String getxN() {
        return xN;
    }

    public void setxN(String xN) {
        this.xN = xN;
    }

    public String getUrlCoTai1() {
        return urlCoTai1;
    }

    public void setUrlCoTai1(String urlCoTai1) {
        this.urlCoTai1 = urlCoTai1;
    }

    public String getUrlCoTai2() {
        return urlCoTai2;
    }

    public void setUrlCoTai2(String urlCoTai2) {
        this.urlCoTai2 = urlCoTai2;
    }

    public String getUrlKoTai1() {
        return urlKoTai1;
    }

    public void setUrlKoTai1(String urlKoTai1) {
        this.urlKoTai1 = urlKoTai1;
    }

    public String getUrlKoTai2() {
        return urlKoTai2;
    }

    public void setUrlKoTai2(String urlKoTai2) {
        this.urlKoTai2 = urlKoTai2;
    }

    public String getChietKhau() {
        return chietKhau;
    }

    public void setChietKhau(String chietKhau) {
        this.chietKhau = chietKhau;
    }

    public String getDonGia() {
        return donGia;
    }

    public void setDonGia(String donGia) {
        this.donGia = donGia;
    }

    public String getSauCK() {
        return sauCK;
    }

    public void setSauCK(String sauCK) {
        this.sauCK = sauCK;
    }

    public Long getThanhTien() {
        return thanhTien;
    }

    public void setThanhTien(Long thanhTien) {
        this.thanhTien = thanhTien;
    }
}
