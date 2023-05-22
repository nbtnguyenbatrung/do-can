package com.dogoo.SystemWeighingSas.unitity.response;


import com.dogoo.SystemWeighingSas.unitity.exception.ClientException;
import org.json.JSONObject;

import java.util.List;

public class ResponseFactory {

    public static Response getSuccessResponse(String message, List entities) {
        return new Response.ResponseBuilders().setCode(Response.OK_CODE).setStatus(Response.OK_STATUS)
                .setMessage(message).setData(entities).build();
    }

    public static Response getSuccessResponse(String message, Object entity) {
        return new Response.ResponseBuilders().setCode(Response.OK_CODE).setStatus(Response.OK_STATUS)
                .setMessage(message).setData(entity).build();
    }

    public static Response getSuccessResponse(String message, JSONObject entity) {
        return new Response.ResponseBuilders().setCode(Response.OK_CODE).setStatus(Response.OK_STATUS)
                .setMessage(message).setData(entity).build();
    }

    public static Response getSuccessResponse(String message) {
        return new Response.ResponseBuilders().setCode(Response.OK_CODE).setStatus(Response.OK_STATUS)
                .setMessage(message).setData(ResultResponse.empty()).build();
    }

    @Deprecated
    public static Response getServerErrorResponse(String message, Object entities) {
        return new Response.ResponseBuilders().setCode(Response.INTERNAL_ERROR_CODE).setStatus(Response.ERROR_STATUS)
                .setMessage(message).setData(entities).build();
    }

    public static Response getServerErrorResponse(ClientException.Message message) {
        return new Response.ResponseBuilders<ResultResponse>()
                .setCode(message.code)
                .setStatus(message.status)
                .setMessage(message.message)
                .setData(ResultResponse.empty())
                .build();
    }

    public static Response getClientErrorResponse(String message) {
        return new Response.ResponseBuilders().setCode(Response.CLIENT_ERROR_CODE)
                .setStatus(Response.ERROR_STATUS)
                .setMessage(message).setData(ResultResponse.empty()).build();
    }

    public static Response getClientErrorResponse(ClientException.Message message) {
        return new Response.ResponseBuilders<ResultResponse>()
                .setCode(message.code)
                .setStatus(message.status)
                .setMessage(message.message)
                .setData(ResultResponse.empty())
                .build();
    }


    public static Response getInfoNotFound(String message) {
        Response response = new Response();
        response.setCode(Response.NOT_FOUND_INFO);
        response.setStatus(Response.ERROR_STATUS);
        response.setMessage(message);
        return response;
    }
}
