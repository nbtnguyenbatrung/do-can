package com.dogoo.SystemWeighingSas.service.impl;

import com.dogoo.SystemWeighingSas.dao.IWeightSlipDao;
import com.dogoo.SystemWeighingSas.mapper.ChartMapper;
import com.dogoo.SystemWeighingSas.model.ChartMapperModel;
import com.dogoo.SystemWeighingSas.model.DataMapperModel;
import com.dogoo.SystemWeighingSas.model.ReportCompareModel;
import com.dogoo.SystemWeighingSas.model.ReportModel;
import com.dogoo.SystemWeighingSas.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class ReportServiceImpl implements ReportService {

    @Autowired
    private IWeightSlipDao iWeightSlipDao;
    @Autowired
    private ChartMapper mapper;

    @Override
    public ReportModel getReportNewDate(String weighingStationCode) {

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime startDate = now.withHour(0).withMinute(0).withSecond(0).withNano(0);
        LocalDateTime endDate = now.withHour(23).withMinute(59).withSecond(59).withNano(0);

        long bill = iWeightSlipDao.countNgayCan(startDate, endDate, weighingStationCode);
        Long netWeight = iWeightSlipDao.sumWeight(startDate, endDate, weighingStationCode);
        Long revenue = iWeightSlipDao.sumRevenue(startDate, endDate, weighingStationCode);
        long customer = iWeightSlipDao.countKh(startDate, endDate, weighingStationCode);

        ReportModel model = new ReportModel();
        model.setBill(bill);
        model.setNetWeight(netWeight == null ? 0 : netWeight);
        model.setRevenue(revenue == null ? 0 : revenue);
        model.setCustomer(customer);
        return model;
    }

    @Override
    public ReportCompareModel getReportCompare(String weighingStationCode,
                                               LocalDateTime startDate,
                                               LocalDateTime endDate,
                                               LocalDateTime startDateCompare,
                                               LocalDateTime endDateCompare) {

        startDate = startDate.withHour(0).withMinute(0).withSecond(0).withNano(0);
        endDate = endDate.withHour(23).withMinute(59).withSecond(59).withNano(0);

        long bill = iWeightSlipDao.countNgayCan(startDate, endDate, weighingStationCode);
        Long netWeight = iWeightSlipDao.sumWeight(startDate, endDate, weighingStationCode);
        Long revenue = iWeightSlipDao.sumRevenue(startDate, endDate, weighingStationCode);

        double billPercent = 0;
        double netWeightPercent = 0;
        double revenuePercent = 0;

        if (startDateCompare != null && endDateCompare != null) {
            startDateCompare = startDateCompare.withHour(0).withMinute(0).withSecond(0).withNano(0);
            endDateCompare = endDateCompare.withHour(23).withMinute(59).withSecond(59).withNano(0);

            long billTemp = iWeightSlipDao.countNgayCan(startDateCompare, endDateCompare, weighingStationCode);
            Long netWeightTemp = iWeightSlipDao.sumWeight(startDateCompare, endDateCompare, weighingStationCode);
            Long revenueTemp = iWeightSlipDao.sumRevenue(startDateCompare, endDateCompare, weighingStationCode);

            if (billTemp != 0) {
                billPercent = (((double) bill / billTemp) - 1) * 100;
            }
            if (netWeightTemp != null && netWeightTemp != 0 && netWeight != null) {
                netWeightPercent = (((double) netWeight / netWeightTemp) - 1) * 100;
            }
            if (revenueTemp != null && revenueTemp != 0 && revenue != null) {
                revenuePercent = (((double) revenue / revenueTemp) - 1) * 100;
            }
        }
        ReportCompareModel model = new ReportCompareModel();
        model.setBill(bill);
        model.setNetWeight(netWeight != null ? netWeight : 0);
        model.setRevenue(revenue != null ? revenue : 0);

        model.setBillPercent((long) billPercent);
        model.setNetWeightPercent((long) netWeightPercent);
        model.setRevenuePercent((long) revenuePercent);
        return model;
    }

    @Override
    public List<ChartMapperModel> getChart(String weighingStationCode,
                                           String type,
                                           LocalDateTime startDate,
                                           LocalDateTime endDate,
                                           LocalDateTime startDateCompare,
                                           LocalDateTime endDateCompare) {

        List<ChartMapperModel> list = new ArrayList<>();
        startDate = startDate.withHour(0).withMinute(0).withSecond(0).withNano(0);
        endDate = endDate.withHour(23).withMinute(59).withSecond(59).withNano(0);

        if (startDateCompare != null && endDateCompare != null){
            startDateCompare = startDateCompare.withHour(0).withMinute(0).withSecond(0).withNano(0);
            endDateCompare = endDateCompare.withHour(23).withMinute(59).withSecond(59).withNano(0);
        }

        switch (type) {
            case "bill":
                getChartBill(weighingStationCode, startDate, endDate,
                        startDateCompare, endDateCompare, list);
                break;
            case "netWeight":
                getChartNetWeight(weighingStationCode, startDate, endDate,
                        startDateCompare, endDateCompare, list);
                break;
            case "revenue":
                getChartRevenue(weighingStationCode, startDate, endDate,
                        startDateCompare, endDateCompare, list);
                break;
            default:
                break;
        }

        return list;
    }

    private void getChartBill(String weighingStationCode,
                              LocalDateTime startDate,
                              LocalDateTime endDate,
                              LocalDateTime startDateCompare,
                              LocalDateTime endDateCompare,
                              List<ChartMapperModel> list) {
        List<Object[]> weightSlipList = iWeightSlipDao
                .groupBillByNgayCan(startDate, endDate, weighingStationCode);
        List<DataMapperModel> chartMapperModelList = mapper.mapDataFromWeightSlip(weightSlipList);
        ChartMapperModel to = new ChartMapperModel();
        to.setName("1");
        to.setList(chartMapperModelList);
        list.add(to);

        if (startDateCompare != null && endDateCompare != null){
            List<Object[]> weightSlipList2 = iWeightSlipDao
                    .groupBillByNgayCan(startDateCompare, endDateCompare, weighingStationCode);
            List<DataMapperModel> chartMapperModelList2 = mapper
                    .mapDataFromWeightSlip(weightSlipList2);
            ChartMapperModel to2 = new ChartMapperModel();
            to2.setName("2");
            to2.setList(chartMapperModelList2);
            list.add(to2);
        }

    }

    private void getChartNetWeight(String weighingStationCode,
                                   LocalDateTime startDate,
                                   LocalDateTime endDate,
                                   LocalDateTime startDateCompare,
                                   LocalDateTime endDateCompare,
                                   List<ChartMapperModel> list) {

        List<Object[]> weightSlipList = iWeightSlipDao
                .groupNetWeightByNgayCan(startDate, endDate, weighingStationCode);
        List<DataMapperModel> chartMapperModelList = mapper.mapDataFromWeightSlip(weightSlipList);
        ChartMapperModel to = new ChartMapperModel();
        to.setName("1");
        to.setList(chartMapperModelList);
        list.add(to);

        if (startDateCompare != null && endDateCompare != null){
            List<Object[]> weightSlipList2 = iWeightSlipDao
                    .groupNetWeightByNgayCan(startDateCompare, endDateCompare, weighingStationCode);
            List<DataMapperModel> chartMapperModelList2 = mapper
                    .mapDataFromWeightSlip(weightSlipList2);
            ChartMapperModel to2 = new ChartMapperModel();
            to2.setName("2");
            to2.setList(chartMapperModelList2);
            list.add(to2);
        }
    }

    private void getChartRevenue(String weighingStationCode,
                                 LocalDateTime startDate,
                                 LocalDateTime endDate,
                                 LocalDateTime startDateCompare,
                                 LocalDateTime endDateCompare,
                                 List<ChartMapperModel> list) {

        List<Object[]> weightSlipList = iWeightSlipDao
                .groupRevenueByNgayCan(startDate, endDate, weighingStationCode);
        List<DataMapperModel> chartMapperModelList = mapper.mapDataFromWeightSlip(weightSlipList);
        ChartMapperModel to = new ChartMapperModel();
        to.setName("1");
        to.setList(chartMapperModelList);
        list.add(to);

        if (startDateCompare != null && endDateCompare != null){
            List<Object[]> weightSlipList2 = iWeightSlipDao
                    .groupRevenueByNgayCan(startDateCompare, endDateCompare, weighingStationCode);
            List<DataMapperModel> chartMapperModelList2 = mapper
                    .mapDataFromWeightSlip(weightSlipList2);
            ChartMapperModel to2 = new ChartMapperModel();
            to2.setName("2");
            to2.setList(chartMapperModelList2);
            list.add(to2);
        }

    }
}
