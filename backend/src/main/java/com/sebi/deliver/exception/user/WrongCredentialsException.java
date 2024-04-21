package com.sebi.deliver.exception.user;

public class WrongCredentialsException extends RuntimeException {
    public WrongCredentialsException() {
        super("Wrong credentials");
    }
}
