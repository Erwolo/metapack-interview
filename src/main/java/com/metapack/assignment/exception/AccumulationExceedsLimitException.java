package com.metapack.assignment.exception;

public class AccumulationExceedsLimitException extends RuntimeException {

    public AccumulationExceedsLimitException(String message) {
        super(message);
    }

    public AccumulationExceedsLimitException(String message, Throwable cause) {
        super(message, cause);
    }
}
