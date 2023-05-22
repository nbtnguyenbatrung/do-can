package com.dogoo.SystemWeighingSas.unitity.exception;

public class InvalidFormatException extends RuntimeException {
    public InvalidFormatException(String s) {
        super(s);
    }

    public InvalidFormatException(String s, Throwable throwable) {
        super(s, throwable);
    }

    public InvalidFormatException(Throwable throwable) {
        super(throwable);
    }

    public InvalidFormatException(String s, Throwable throwable, boolean b, boolean b1) {
        super(s, throwable, b, b1);
    }
}
