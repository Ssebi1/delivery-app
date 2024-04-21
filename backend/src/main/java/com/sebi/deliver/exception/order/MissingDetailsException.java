package com.sebi.deliver.exception.order;

public class MissingDetailsException extends RuntimeException{
    public MissingDetailsException() {
        super("Missing details. Please fill in your details in the profile section");
    }
}
