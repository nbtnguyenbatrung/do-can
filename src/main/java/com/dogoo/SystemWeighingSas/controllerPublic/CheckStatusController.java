package com.dogoo.SystemWeighingSas.controllerPublic;

import com.dogoo.SystemWeighingSas.model.Status;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
@RequestMapping("/public/dogoo/status")
public class CheckStatusController {

    @GetMapping()
    public ResponseEntity<Status> getStatus() {
        Status status = new Status();
        status.setStatus("ok");
        return ResponseEntity.status(200).body(status);
    }

}
