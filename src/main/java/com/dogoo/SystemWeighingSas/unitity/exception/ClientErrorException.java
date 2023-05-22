package com.dogoo.SystemWeighingSas.unitity.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class ClientErrorException extends RuntimeException {
    private ClientException.Message error;

    public ClientErrorException() {
    }

    public ClientErrorException(String error) {
        super(error);
    }

    public ClientErrorException(ClientException.Message error) {
        super(error.message);
        this.error = error;
    }
}
