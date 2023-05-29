package com.dogoo.SystemWeighingSas.service.impl;

import com.dogoo.SystemWeighingSas.dao.IWeightSlipDao;
import com.dogoo.SystemWeighingSas.model.ReportCompareModel;
import com.dogoo.SystemWeighingSas.model.ReportModel;
import com.dogoo.SystemWeighingSas.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ReportServiceImpl implements ReportService {

    @Autowired
    private IWeightSlipDao iWeightSlipDao;

    @Override
    public ReportModel getReportNewDate(String weighingStationCode) {

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime startDate = now.withHour(0).withMinute(0).withSecond(0).withNano(0);
        LocalDateTime endDate = now.withHour(23).withMinute(59).withSecond(59).withNano(0);

        long bill = iWeightSlipDao.countNgayCan(startDate, endDate, weighingStationCode);
        long netWeight = iWeightSlipDao.sumWeight(startDate, endDate, weighingStationCode);
        long revenue = iWeightSlipDao.sumRevenue(startDate, endDate, weighingStationCode);
        long customer = iWeightSlipDao.countKh(startDate, endDate, weighingStationCode);

        ReportModel model = new ReportModel();
        model.setBill(bill);
        model.setNetWeight(netWeight);
        model.setRevenue(revenue);
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
        long netWeight = iWeightSlipDao.sumWeight(startDate, endDate, weighingStationCode);
        long revenue = iWeightSlipDao.sumRevenue(startDate, endDate, weighingStationCode);

        long billPercent = 0;
        long netWeightPercent = 0;
        long revenuePercent = 0;

        if (startDateCompare != null && endDateCompare != null){
            startDateCompare = startDateCompare.withHour(0).withMinute(0).withSecond(0).withNano(0);
            endDateCompare = endDate.withHour(23).withMinute(59).withSecond(59).withNano(0);

            long billTemp = iWeightSlipDao.countNgayCan(startDateCompare, endDateCompare, weighingStationCode);
            long netWeightTemp = iWeightSlipDao.sumWeight(startDateCompare, endDateCompare, weighingStationCode);
            long revenueTemp = iWeightSlipDao.sumRevenue(startDateCompare, endDateCompare, weighingStationCode);

            if (billTemp != 0){
                billPercent = ((bill / billTemp) - 1) * 100 ;
            }
            if (netWeightTemp != 0){
                netWeightPercent = ((netWeight / netWeightTemp) - 1) * 100 ;
            }
            if (revenueTemp != 0){
                revenuePercent = ((revenue / revenueTemp) - 1) * 100 ;
            }
        }
        ReportCompareModel model = new ReportCompareModel();
        model.setBill(bill);
        model.setNetWeight(netWeight);
        model.setRevenue(revenue);

        model.setBillPercent(billPercent);
        model.setNetWeightPercent(netWeightPercent);
        model.setRevenuePercent(revenuePercent);
        return model;
    }
}
