package com.dogoo.SystemWeighingSas.config;

import com.dogoo.SystemWeighingSas.entity.WeightSlip;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;

public class Constants {
    public static ObjectMapper SERIALIZER = new ObjectMapper();

    public static final String ACTION_CREATE = "create";
    public static final String ACTION_UPDATE = "update";
    public static final String ACTION_DELETE = "delete";

    public static List<Future<List<WeightSlip>>> jobSubmit = new ArrayList<>();

    public static int size = 100;
}
