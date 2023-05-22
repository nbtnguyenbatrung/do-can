package com.dogoo.SystemWeighingSas.mapper;

import com.dogoo.SystemWeighingSas.entity.WeightSlip;
import com.dogoo.SystemWeighingSas.model.WeightSlipDto;
import com.dogoo.SystemWeighingSas.model.WeightSlipEntry;
import com.dogoo.SystemWeighingSas.model.WeightSlipMapperModel;
import org.springframework.stereotype.Service;

@Service
public class WeightMapper {

    public WeightSlipDto mapDtoFromModel(WeightSlipMapperModel model){
        WeightSlipDto to = new WeightSlipDto();
        to.setMaPhieu(model.getMaPhieu());
        to.setKhachHang(model.getKhachHang());
        to.setTenHang(model.getTenHang());
        to.setMaHang(model.getMaHang());
        to.setMaKH(model.getMaKH());
        to.setSoXe(model.getSoXe());
        to.setCoTai(model.getCoTai());
        to.setTareWeight(model.getTareWeight());
        to.setHang(model.getHang());
        to.setNgayCan(model.getNgayCan());
        to.setGioCoTai(model.getGioCoTai());
        to.setGioKTai(model.getGioKTai());
        to.setGhiChu(model.getGhiChu());
        to.setxN(model.getXN());
        to.setUrlCoTai1(model.getUrlCoTai1());
        to.setUrlCoTai2(model.getUrlCoTai2());
        to.setUrlKoTai1(model.getUrlKoTai1());
        to.setUrlKoTai2(model.getUrlKoTai2());
        to.setChietKhau(model.getChietKhau());
        to.setSauCK(model.getSauCK());
        to.setThanhTien(model.getThanhTien());

        return to;
    }

    public WeightSlipEntry mapEntryFromEntity(WeightSlip model){
        WeightSlipEntry to = new WeightSlipEntry();
        to.setMaPhieu(model.getMaPhieu());
        to.setKhachHang(model.getKhachHang());
        to.setTenHang(model.getTenHang());
        to.setMaHang(model.getMaHang());
        to.setMaKH(model.getMaKH());
        to.setSoXe(model.getSoXe());
        to.setCoTai(model.getCoTai());
        to.setTareWeight(model.getTareWeight());
        to.setHang(model.getHang());
        to.setNgayCan(model.getNgayCan());
        to.setGioCoTai(model.getGioCoTai());
        to.setGioKTai(model.getGioKTai());
        to.setGhiChu(model.getGhiChu());
        to.setxN(model.getXN());
        to.setUrlCoTai1(model.getUrlCoTai1());
        to.setUrlCoTai2(model.getUrlCoTai2());
        to.setUrlKoTai1(model.getUrlKoTai1());
        to.setUrlKoTai2(model.getUrlKoTai2());
        to.setChietKhau(model.getChietKhau());
        to.setSauCK(model.getSauCK());
        to.setThanhTien(model.getThanhTien());

        return to;
    }
}
