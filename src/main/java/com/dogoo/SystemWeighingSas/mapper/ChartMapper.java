package com.dogoo.SystemWeighingSas.mapper;

import com.dogoo.SystemWeighingSas.entity.WeightSlip;
import com.dogoo.SystemWeighingSas.model.DataMapperModel;
import com.dogoo.SystemWeighingSas.model.WeightSlipCountSum;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ChartMapper {

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public List<DataMapperModel> mapDataFromWeightSlip(List<WeightSlipCountSum> list){
        return list .stream().map(this::mapperModelFromEntry)
                .collect(Collectors.toList());
    }

    public DataMapperModel mapperModelFromEntry(WeightSlipCountSum from){
        DataMapperModel to = new DataMapperModel();
        to.setXAxis(from.getNgayCan().format(formatter));
        to.setValue(from.getValue());
        return to;
    }
}
