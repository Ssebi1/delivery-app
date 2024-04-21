package com.sebi.deliver.exception;

import com.sebi.deliver.exception.coupon.InvalidDiscountException;
import com.sebi.deliver.exception.order.MissingDetailsException;
import com.sebi.deliver.exception.product.SalePriceBiggerThanPriceException;
import com.sebi.deliver.exception.user.*;
import com.sebi.deliver.model.ApiError;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class ExceptionHandler extends ResponseEntityExceptionHandler {
    @org.springframework.web.bind.annotation.ExceptionHandler(
            {MissingFieldsException.class,
                    EmailAlreadyExistsException.class,
                    WrongCredentialsException.class,
                    GenericException.class,
                    SalePriceBiggerThanPriceException.class,
                    MissingDetailsException.class,
                    InvalidDiscountException.class})
    public ResponseEntity handle(Exception exception) {
        ApiError apiError = new ApiError(
                org.springframework.http.HttpStatus.BAD_REQUEST,
                exception.getMessage(),
                exception
        );
        return ResponseEntity.badRequest()
                .body(apiError);
    }
}
