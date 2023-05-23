package com.dogoo.SystemWeighingSas.controllerPublic;

import com.dogoo.SystemWeighingSas.model.DatabaseKeyMapperModel;
import com.dogoo.SystemWeighingSas.service.DatabaseKeyService;
import com.dogoo.SystemWeighingSas.validator.DatabaseKeyValidator;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/public/dogoo/databaseKey")
public class DatabaseKeyController {

    private final DatabaseKeyService databaseKeyService;
    private final DatabaseKeyValidator validator;

    public DatabaseKeyController(DatabaseKeyService databaseKeyService,
                                 DatabaseKeyValidator validator) {
        this.databaseKeyService = databaseKeyService;
        this.validator = validator;
    }

    @PostMapping("/create")
    public ResponseEntity<String> createDatabaseKey(@RequestBody DatabaseKeyMapperModel model) {
        try {
            ResponseEntity<String> response = validator.validatorCheck(
                    model.getKey(), model.getDatabaseKey());

            if (response == null){
                databaseKeyService.createDatabaseKey(model);
            }
            return response;
        } catch (Exception exception) {
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/check")
    public ResponseEntity<String> checkKey(@RequestParam String key,
                                           @RequestParam String databaseKey) {
        try {
            ResponseEntity<String> response = validator.validatorCheck(key, databaseKey);

            if (response != null){
                new ResponseEntity<>("Tồn tại key ", HttpStatus.OK);
            }

            return response;
        } catch (Exception exception) {
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

}
