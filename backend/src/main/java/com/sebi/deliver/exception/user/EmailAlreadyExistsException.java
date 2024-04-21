package com.sebi.deliver.exception.user;

public class EmailAlreadyExistsException extends RuntimeException {
    public EmailAlreadyExistsException() {
        super("There's already an account with this email");
    }
}
