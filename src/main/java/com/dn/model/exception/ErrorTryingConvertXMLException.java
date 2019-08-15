package com.dn.model.exception;

public class ErrorTryingConvertXMLException extends RuntimeException {

    public ErrorTryingConvertXMLException(String message, Throwable cause) {
        super(message, cause);
    }

    public ErrorTryingConvertXMLException(Throwable cause) {
        super(cause);
    }
}
