package com.sebi.deliver.exception.product;

public class SalePriceBiggerThanPriceException extends RuntimeException{
    public SalePriceBiggerThanPriceException() {
        super("Sale price bigger than price");
    }
}
