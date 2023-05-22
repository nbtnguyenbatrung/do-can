package com.dogoo.SystemWeighingSas.thread;

import com.dogoo.SystemWeighingSas.config.Constants;
import com.dogoo.SystemWeighingSas.dao.IWeightSlipDao;
import com.dogoo.SystemWeighingSas.entity.WeightSlip;
import com.dogoo.SystemWeighingSas.mapper.WeightMapper;
import com.dogoo.SystemWeighingSas.model.WeightSlipDto;
import com.dogoo.SystemWeighingSas.model.WeightSlipEntry;
import com.dogoo.SystemWeighingSas.model.WeightSlipMapperModel;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.concurrent.Callable;

@Slf4j
public class JobCompareUpdate implements Callable<List<WeightSlip>> {
    private List<WeightSlipMapperModel> list;
    private final IWeightSlipDao iWeightSlipDao;
    private final String key;
    private final String databaseKey;
    private final WeightMapper mapper;


    public JobCompareUpdate(List<WeightSlipMapperModel> list,
                            IWeightSlipDao iWeightSlipDao, String key, String databaseKey, WeightMapper mapper) {
        this.list = list;
        this.iWeightSlipDao = iWeightSlipDao;
        this.key = key;
        this.databaseKey = databaseKey;
        this.mapper = mapper;
    }

    @Override
    public List<WeightSlip> call() throws Exception {

        list.forEach(model -> {
            WeightSlip to = iWeightSlipDao.getWeightSlipByMaPhieu(model.getMaPhieu());

            if (to == null){
                WeightSlip weightSlip = Constants.SERIALIZER.convertValue(model, WeightSlip.class);
                weightSlip.setKey(key);
                weightSlip.setDatabaseKey(databaseKey);
                weightSlip.setAction(Constants.ACTION_CREATE);
                iWeightSlipDao.save(weightSlip);
            }else{
                WeightSlipDto dto = mapper.mapDtoFromModel(model);
                WeightSlipEntry entry = mapper.mapEntryFromEntity(to);

                String stringWeightSlipDto = "";
                String stringWeightSlipEntry = "";
                try {
                    stringWeightSlipDto = Constants.SERIALIZER.writeValueAsString(dto);
                    stringWeightSlipEntry = Constants.SERIALIZER.writeValueAsString(entry);
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }

                if (!stringWeightSlipDto.equals(stringWeightSlipEntry)){
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
                    to.setXN(model.getXN());
                    to.setUrlCoTai1(model.getUrlCoTai1());
                    to.setUrlCoTai2(model.getUrlCoTai2());
                    to.setUrlKoTai1(model.getUrlKoTai1());
                    to.setUrlKoTai2(model.getUrlKoTai2());
                    to.setChietKhau(model.getChietKhau());
                    to.setDonGia(model.getDonGia());
                    to.setSauCK(model.getSauCK());
                    to.setThanhTien(model.getThanhTien());
                }
            }
        });
        return null;
    }
}
