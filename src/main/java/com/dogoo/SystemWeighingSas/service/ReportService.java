package com.dogoo.SystemWeighingSas.service;

import com.dogoo.SystemWeighingSas.model.ChartMapperModel;
import com.dogoo.SystemWeighingSas.model.ReportCompareModel;
import com.dogoo.SystemWeighingSas.model.ReportModel;

import java.time.LocalDateTime;
import java.util.List;

public interface ReportService {

    ReportModel getReportNewDate(String weighingStationCode);

    ReportCompareModel getReportCompare(String weighingStationCode,
                                        LocalDateTime startDate,
                                        LocalDateTime endDate,
                                        LocalDateTime startDateCompare,
                                        LocalDateTime endDateCompare);

    ChartMapperModel getChart(String weighingStationCode,
                              String type,
                              LocalDateTime startDate,
                              LocalDateTime endDate,
                              LocalDateTime startDateCompare,
                              LocalDateTime endDateCompare);
}
