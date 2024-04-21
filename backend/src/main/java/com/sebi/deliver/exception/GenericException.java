package com.sebi.deliver.exception;

public class GenericException  extends RuntimeException {
    public GenericException() {
        super("Could not process request. Please try again later");
    }
}
