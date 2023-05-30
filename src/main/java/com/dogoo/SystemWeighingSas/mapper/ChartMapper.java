package com.dogoo.SystemWeighingSas.mapper;

import com.dogoo.SystemWeighingSas.model.DataMapperModel;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ChartMapper {

    public List<DataMapperModel> mapDataFromWeightSlip(List<Object[]> list){
        return list.stream().map(this::mapperModelFromEntry)
                .collect(Collectors.toList());
    }

    public DataMapperModel mapperModelFromEntry(Object[] objects){
        DataMapperModel to = new DataMapperModel();
        String s = objects[0].toString();
        to.setXAxis(s.substring(0, s.indexOf("T")));
        to.setValue( Double.parseDouble(objects[1].toString()));
        return to;
    }
}
