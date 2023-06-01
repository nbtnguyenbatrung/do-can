package com.dogoo.SystemWeighingSas.service.impl;

import com.dogoo.SystemWeighingSas.dao.IWeightSlipDao;
import com.dogoo.SystemWeighingSas.entity.WeightSlip;
import com.dogoo.SystemWeighingSas.mapper.ChartMapper;
import com.dogoo.SystemWeighingSas.model.*;
import com.dogoo.SystemWeighingSas.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

        long bill = iWeightSlipDao.countNgayCan(Timestamp.valueOf(startDate),
                Timestamp.valueOf(endDate), weighingStationCode);
        Long netWeight = iWeightSlipDao.sumWeight(Timestamp.valueOf(startDate),
                Timestamp.valueOf(endDate), weighingStationCode);
        Long revenue = iWeightSlipDao.sumRevenue(Timestamp.valueOf(startDate),
                Timestamp.valueOf(endDate), weighingStationCode);
        long customer = iWeightSlipDao.countKh(Timestamp.valueOf(startDate),
                Timestamp.valueOf(endDate), weighingStationCode);

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

        long bill = iWeightSlipDao.countNgayCan(Timestamp.valueOf(startDate),
                Timestamp.valueOf(endDate), weighingStationCode);
        Long netWeight = iWeightSlipDao.sumWeight(Timestamp.valueOf(startDate),
                Timestamp.valueOf(endDate), weighingStationCode);
        Long revenue = iWeightSlipDao.sumRevenue(Timestamp.valueOf(startDate),
                Timestamp.valueOf(endDate), weighingStationCode);

        double billPercent = 0;
        double netWeightPercent = 0;
        double revenuePercent = 0;

        if (startDateCompare != null && endDateCompare != null) {
            startDateCompare = startDateCompare.withHour(0).withMinute(0).withSecond(0).withNano(0);
            endDateCompare = endDateCompare.withHour(23).withMinute(59).withSecond(59).withNano(0);

            long billTemp = iWeightSlipDao.countNgayCan(Timestamp.valueOf(startDateCompare),
                    Timestamp.valueOf(endDateCompare), weighingStationCode);
            Long netWeightTemp = iWeightSlipDao.sumWeight(Timestamp.valueOf(startDateCompare),
                    Timestamp.valueOf(endDateCompare), weighingStationCode);
            Long revenueTemp = iWeightSlipDao.sumRevenue(Timestamp.valueOf(startDateCompare),
                    Timestamp.valueOf(endDateCompare), weighingStationCode);

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
    public ChartMapperModel getChart(String weighingStationCode,
                                     String type,
                                     LocalDateTime startDate,
                                     LocalDateTime endDate,
                                     LocalDateTime startDateCompare,
                                     LocalDateTime endDateCompare) {

        double dateDiff = Duration.between(startDate, endDate).toDays() + 1;
        long jump = (long) Math.ceil(dateDiff / 30);

        List<LocalDate> list = startDate.toLocalDate()
                .datesUntil(endDate.toLocalDate().plusDays(1))
                .filter(localDate ->
                        Period.between(startDate.toLocalDate(), localDate).getDays() % jump == 0
                )
                .collect(Collectors.toList());

        ChartMapperModel model = new ChartMapperModel();
        model.setJump(jump);
        switch (type) {
            case "bill":
                getChartBill(weighingStationCode, list,
                        startDateCompare, endDateCompare, model, jump);
                break;
            case "netWeight":
                getChartNetWeight(weighingStationCode, startDate, endDate,
                        startDateCompare, endDateCompare, model, jump);
                break;
            case "revenue":
                getChartRevenue(weighingStationCode, startDate, endDate,
                        startDateCompare, endDateCompare, model, jump);
                break;
            default:
                break;
        }

        return model;
    }

    private void getChartBill(String weighingStationCode,
                              List<LocalDate> listCurrentPeriodDate,
                              LocalDateTime startDateCompare,
                              LocalDateTime endDateCompare,
                              ChartMapperModel model,
                              long jump) {

        List<String> nameXAxis = new ArrayList<>();
        List<Long> currentPeriod = new ArrayList<>();
        listCurrentPeriodDate.forEach(localDate -> {
            nameXAxis.add(localDate.toString());
            currentPeriod.add(iWeightSlipDao.countNgayCan(
                    Timestamp.valueOf(localDate.atStartOfDay()),
                    Timestamp.valueOf(localDate.plusDays(jump-1).atTime(LocalTime.MAX)),
                    weighingStationCode
            ));
        });

        System.out.println( "nameXAxis sixze " + nameXAxis.size());
        System.out.println( "currentPeriod sixze " + currentPeriod.size());
        model.setNameXAxis(nameXAxis);
        model.setCurrentPeriod(currentPeriod);

        if (startDateCompare != null && endDateCompare != null) {

            List<LocalDate> list = startDateCompare.toLocalDate()
                    .datesUntil(endDateCompare.toLocalDate().plusDays(1))
                    .filter(localDate ->
                            Period.between(startDateCompare.toLocalDate(), localDate).getDays() % jump == 0
                    )
                    .collect(Collectors.toList());
            long sizeListCurrentPeriodDate = listCurrentPeriodDate.size();
            long size = list.size();

            if (size != sizeListCurrentPeriodDate){
                int index = list.size() - 1;
                list.remove(index);
            }

            List<Long> samePeriod = new ArrayList<>();
            list.forEach(localDate -> {
                if (samePeriod.size() == sizeListCurrentPeriodDate - 1){
                    samePeriod.add(iWeightSlipDao.countNgayCan(
                            Timestamp.valueOf(localDate.atStartOfDay()),
                            Timestamp.valueOf(endDateCompare.with(LocalTime.MAX)),
                            weighingStationCode
                    ));
                }else {
                    samePeriod.add(iWeightSlipDao.countNgayCan(
                            Timestamp.valueOf(localDate.atStartOfDay()),
                            Timestamp.valueOf(localDate.plusDays(jump-1).atTime(LocalTime.MAX)),
                            weighingStationCode
                    ));
                }
            });
            model.setSamePeriod(samePeriod);
        }

    }

    private void getChartNetWeight(String weighingStationCode,
                                   LocalDateTime startDate,
                                   LocalDateTime endDate,
                                   LocalDateTime startDateCompare,
                                   LocalDateTime endDateCompare,
                                   ChartMapperModel model,
                                   long jump) {

        List<Object[]> weightSlipList = iWeightSlipDao
                .groupNetWeightByNgayCan(Timestamp.valueOf(startDate),
                        Timestamp.valueOf(endDate), weighingStationCode);
        List<DataMapperModel> chartMapperModelList = mapper.mapDataFromWeightSlip(weightSlipList);
        ChartMapperModel to = new ChartMapperModel();
        to.setName("1");
        to.setList(chartMapperModelList);
//        list.add(to);

        if (startDateCompare != null && endDateCompare != null) {
            List<Object[]> weightSlipList2 = iWeightSlipDao
                    .groupNetWeightByNgayCan(Timestamp.valueOf(startDateCompare),
                            Timestamp.valueOf(endDateCompare), weighingStationCode);
            List<DataMapperModel> chartMapperModelList2 = mapper
                    .mapDataFromWeightSlip(weightSlipList2);
            ChartMapperModel to2 = new ChartMapperModel();
            to2.setName("2");
            to2.setList(chartMapperModelList2);
//            list.add(to2);
        }
    }

    private void getChartRevenue(String weighingStationCode,
                                 LocalDateTime startDate,
                                 LocalDateTime endDate,
                                 LocalDateTime startDateCompare,
                                 LocalDateTime endDateCompare,
                                 ChartMapperModel model,
                                 long jump) {

        List<Object[]> weightSlipList = iWeightSlipDao
                .groupRevenueByNgayCan(Timestamp.valueOf(startDate),
                        Timestamp.valueOf(endDate), weighingStationCode);
        List<DataMapperModel> chartMapperModelList = mapper.mapDataFromWeightSlip(weightSlipList);
        ChartMapperModel to = new ChartMapperModel();
        to.setName("1");
        to.setList(chartMapperModelList);
//        list.add(to);

        if (startDateCompare != null && endDateCompare != null) {
            List<Object[]> weightSlipList2 = iWeightSlipDao
                    .groupRevenueByNgayCan(Timestamp.valueOf(startDateCompare),
                            Timestamp.valueOf(endDateCompare), weighingStationCode);
            List<DataMapperModel> chartMapperModelList2 = mapper
                    .mapDataFromWeightSlip(weightSlipList2);
            ChartMapperModel to2 = new ChartMapperModel();
            to2.setName("2");
            to2.setList(chartMapperModelList2);
//            list.add(to2);
        }

    }
}
