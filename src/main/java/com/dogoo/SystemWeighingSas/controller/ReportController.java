package com.dogoo.SystemWeighingSas.controller;

import com.dogoo.SystemWeighingSas.enumEntity.TypeEnum;
import com.dogoo.SystemWeighingSas.service.ReportService;
import com.dogoo.SystemWeighingSas.unitity.response.Response;
import com.dogoo.SystemWeighingSas.unitity.response.ResponseFactory;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/o/dogoo/report")
public class ReportController {

    private final ReportService reportService;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @GetMapping("/now")
    public Response getReportNow(@RequestParam(name = "weighingStationCode") String weighingStationCode) {
        try {
            return ResponseFactory.getSuccessResponse(Response.SUCCESS,
                    reportService.getReportNewDate(weighingStationCode));
        } catch (Exception exception) {
            return ResponseFactory.getClientErrorResponse(exception.getMessage());
        }
    }

    @GetMapping("/compare")
    public Response getReportCompare(@RequestParam(name = "weighingStationCode") String weighingStationCode,
                                     @RequestParam(name = "startDate")
                                     @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
                                     @RequestParam(name = "endDate")
                                     @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate,
                                     @RequestParam(name = "startDateCompare", required = false)
                                     @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDateCompare,
                                     @RequestParam(name = "endDateCompare", required = false)
                                     @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDateCompare) {
        try {
            return ResponseFactory.getSuccessResponse(Response.SUCCESS,
                    reportService.getReportCompare(weighingStationCode, startDate,
                            endDate, startDateCompare, endDateCompare));
        } catch (Exception exception) {
            return ResponseFactory.getClientErrorResponse(exception.getMessage());
        }
    }

    @GetMapping("/chart")
    public Response getReportChart(@RequestParam(name = "weighingStationCode") String weighingStationCode,
                                   @RequestParam(name = "type") TypeEnum type,
                                   @RequestParam(name = "startDate")
                                   @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
                                   @RequestParam(name = "endDate")
                                   @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate,
                                   @RequestParam(name = "startDateCompare", required = false)
                                   @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDateCompare,
                                   @RequestParam(name = "endDateCompare", required = false)
                                   @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDateCompare) {
        try {
            return ResponseFactory.getSuccessResponse(Response.SUCCESS,
                    reportService.getChart(weighingStationCode, type.name(), startDate,
                            endDate, startDateCompare, endDateCompare));
        } catch (Exception exception) {
            return ResponseFactory.getClientErrorResponse(exception.getMessage());
        }
    }
}
