package com.dogoo.SystemWeighingSas.model;

import java.time.LocalDateTime;

public class WeightSlipCountSum {

    private LocalDateTime ngayCan;
    private double value;

    public WeightSlipCountSum(LocalDateTime ngayCan, double value) {
        this.ngayCan = ngayCan;
        this.value = value;
    }

    public LocalDateTime getNgayCan() {
        return ngayCan;
    }

    public void setNgayCan(LocalDateTime ngayCan) {
        this.ngayCan = ngayCan;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }
}
