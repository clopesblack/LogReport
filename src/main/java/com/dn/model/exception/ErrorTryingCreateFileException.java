package com.dn.model.exception;

public class ErrorTryingCreateFileException extends RuntimeException {

    public ErrorTryingCreateFileException(String message, Throwable cause) {
        super(message, cause);
    }

    public ErrorTryingCreateFileException(Throwable cause) {
        super(cause);
    }
}