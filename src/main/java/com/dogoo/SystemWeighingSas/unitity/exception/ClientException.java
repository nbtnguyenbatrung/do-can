package com.dogoo.SystemWeighingSas.unitity.exception;

public class ClientException extends Exception {
    private static final long serialVersionUID = 1L;

    private static final Integer SERVER_ERROR_CODE = 1000;

    private static final Integer ERROR_STATUS = 0;

    public ClientException() {
    }

    public ClientException(String s) {
        super(s);
    }

    public ClientException(String s, Throwable throwable) {
        super(s, throwable);
    }

    public enum Message {
        SERVER_ERROR(SERVER_ERROR_CODE, "Lỗi hệ thống, thử lại sau", ERROR_STATUS);

        public int code;
        public int status;
        public String message;

        Message(int code, String message, int status) {
            this.code = code;
            this.status = status;
            this.message = message;
        }
    }
}
