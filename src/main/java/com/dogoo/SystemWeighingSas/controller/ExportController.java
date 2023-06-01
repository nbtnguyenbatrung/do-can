package com.dogoo.SystemWeighingSas.controller;


import com.dogoo.SystemWeighingSas.dao.IWeightSlipDao;
import com.dogoo.SystemWeighingSas.dao.WeightSlipCriteriaRepository;
import com.dogoo.SystemWeighingSas.entity.WeightSlip;
import com.dogoo.SystemWeighingSas.model.WeightSlipCriteria;
import com.dogoo.SystemWeighingSas.service.ExportDataService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/o/dogoo/export")

public class ExportController {

    private final Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private IWeightSlipDao iWeightSlipDao;
    @Autowired
    private WeightSlipCriteriaRepository weightSlipCriteriaRepository;


    @GetMapping("/excel/{code}")
    public void exportToExcel(@PathVariable("code") String code,
                              @RequestParam(name = "pageSize", defaultValue = "10", required = false) Integer pageSize,
                              @RequestParam(name = "page", defaultValue = "0", required = false) Integer page,
                              WeightSlipCriteria weightSlipCriteria,
                              HttpServletResponse response) throws IOException {

        response.setContentType("application/octet-stream");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());

        logger.info(" Export phieu " + currentDateTime);

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=phieu_can_" + currentDateTime + ".xlsx";
        response.setHeader(headerKey, headerValue);

        weightSlipCriteria.setDatabaseKey(code);
        Page<WeightSlip> weightSlipPage = weightSlipCriteriaRepository
                .findAllWithFilters(10, 0, weightSlipCriteria);
        List<WeightSlip> listUsers = weightSlipPage.getContent();

        ExportDataService excelExporter = new ExportDataService(listUsers);

        excelExporter.export(response);
    }
}
