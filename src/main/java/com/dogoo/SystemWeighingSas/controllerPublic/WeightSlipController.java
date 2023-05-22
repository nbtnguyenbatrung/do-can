package com.dogoo.SystemWeighingSas.controllerPublic;

import com.dogoo.SystemWeighingSas.entity.WeightSlip;
import com.dogoo.SystemWeighingSas.model.WeightSlipMapperModel;
import com.dogoo.SystemWeighingSas.service.WeightSlipService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/public/dogoo/weight-slip")
public class WeightSlipController {

    private final WeightSlipService weightSlipService;

    public WeightSlipController(WeightSlipService weightSlipService) {
        this.weightSlipService = weightSlipService;
    }

    @PostMapping("/sync-data")
    public ResponseEntity<String> SyncDatabaseKey(@RequestParam String key,
                                                  @RequestParam String databaseKey,
                                                  @RequestBody List<WeightSlipMapperModel> list) {
        try {
            weightSlipService.SyncData(key, databaseKey, list);
            return new ResponseEntity<>("Đồng bộ hóa dữ liệu thành công", HttpStatus.OK);
        } catch (Exception exception) {
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/create-data")
    public ResponseEntity<String> createWeightSlip(@RequestParam String key,
                                                   @RequestParam String databaseKey,
                                                   @RequestBody List<WeightSlipMapperModel> list) {
        try {
            weightSlipService.SyncDataNew(key, databaseKey, list);
            return new ResponseEntity<>("Đồng bộ hóa dữ liệu thành công", HttpStatus.OK);
        } catch (Exception exception) {
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/get-data")
    public WeightSlip getWeightSlip() {
        return weightSlipService.getWeightSlipsOrder();
    }

    @GetMapping("/get-action")
    public List<WeightSlip> getWeightSlipAction() {
        return weightSlipService.getWeightSlipsAction();
    }

    @GetMapping("/list")
    public List<WeightSlip> getListDatabaseKey() {
        return weightSlipService.getWeightSlips();
    }
}
