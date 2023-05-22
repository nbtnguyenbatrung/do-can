package com.dogoo.SystemWeighingSas.controllerPublic;

import com.dogoo.SystemWeighingSas.model.DatabaseKeyMapperModel;
import com.dogoo.SystemWeighingSas.service.DatabaseKeyService;
import com.dogoo.SystemWeighingSas.validator.DatabaseKeyValidator;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

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
            ResponseEntity<String> response = validator.validatorNotFoundKey(model.getKey());

            if (response == null){
                databaseKeyService.createDatabaseKey(model);
            }
            List<String> s = new ArrayList<>();
            Page<String> pages = new PageImpl<String>(s);
            return response;
        } catch (Exception exception) {
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/check")
    public ResponseEntity<String> checkKey(@RequestParam String key) {
        try {
            ResponseEntity<String> response = validator.validatorNotFoundKey(key);

            if (response != null){
                new ResponseEntity<>("Tồn tại key ", HttpStatus.OK);
            }

            return response;
        } catch (Exception exception) {
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

}
