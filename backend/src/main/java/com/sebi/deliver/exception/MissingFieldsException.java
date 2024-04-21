package com.sebi.deliver.exception;

public class MissingFieldsException extends RuntimeException {
    public MissingFieldsException() {
        super("Missing fields");
    }
}
