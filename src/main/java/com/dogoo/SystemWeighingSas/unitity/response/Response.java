package com.dogoo.SystemWeighingSas.unitity.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true, value = {"hibernateLazyInitializer", "handler"})
public class Response<E> {
    public static final int OK_CODE = 200;
    public static final int INTERNAL_ERROR_CODE = 500;
    public static final int CLIENT_ERROR_CODE = 400;
    public static final int NOT_FOUND_INFO = 204;
    public static final int OK_STATUS = 1;
    public static final int ERROR_STATUS = 0;
    public static final String SUCCESS = "SUCCESS";
    private String message;
    private Integer code;
    private Integer status;
    private E result;

    private Response(ResponseBuilders<E> builder) {
        this.message = builder.getMessage();
        this.code = builder.getCode();
        this.status = builder.getStatus();
        this.result = builder.getData();
    }

    public Response() {
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public E getResult() {
        return result;
    }

    public void setResult(E result) {
        this.result = result;
    }

    public static class ResponseBuilders<E> {
        private String message;
        private Integer code;
        private Integer status;
        private E data;

        public String getMessage() {
            return message;
        }

        public ResponseBuilders<E> setMessage(String message) {
            this.message = message;
            return this;
        }

        public Integer getCode() {
            return code;
        }

        public ResponseBuilders<E> setCode(Integer code) {
            this.code = code;
            return this;
        }

        public Integer getStatus() {
            return status;
        }

        public ResponseBuilders<E> setStatus(Integer status) {
            this.status = status;
            return this;
        }

        public E getData() {
            return data;
        }

        public ResponseBuilders<E> setData(E data) {
            this.data = data;
            return this;
        }

        public Response<E> build() {
            return new Response<>(this);
        }

        public String buildString() throws IOException {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("message", "ok");
            jsonObject.put("code", 200);
            jsonObject.put("status", 1);
            if (data instanceof JSONObject) {
                jsonObject.put("result", new JSONObject(new ObjectMapper().writeValueAsString(data)));
            } else if (data instanceof JSONArray) {
                jsonObject.put("result", new JSONArray(new ObjectMapper().writeValueAsString(data)));
            } else {
                jsonObject.put("result", data);
            }
            return jsonObject.toString();
        }
    }
}

