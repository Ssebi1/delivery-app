package com.sebi.deliver.exception.coupon;

public class InvalidDiscountException extends RuntimeException{
    public InvalidDiscountException() {
        super("Invalid discount");
    }
}
