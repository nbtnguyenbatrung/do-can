package com.dogoo.SystemWeighingSas.unitity.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class InternalServerException extends RuntimeException {
    private ClientException.Message error;

    public InternalServerException(String error) {
        super(error);
    }

    public InternalServerException() {
        this.error = ClientException.Message.SERVER_ERROR;
    }

    public InternalServerException(ClientException.Message error) {
        super(error.message);
        this.error = error;
    }
}
