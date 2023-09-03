package com.bitside.shoppingbasket.adapter.web.exception;

import com.bitside.shoppingbasket.domain.application.port.exception.MultipleDealsException;
import com.bitside.shoppingbasket.domain.application.port.exception.ResourceNotFoundException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public final ResponseEntity<String> handleResourceNotFoundExceptions(ResourceNotFoundException resourceNotFoundException) {
        return new ResponseEntity<>(resourceNotFoundException.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MultipleDealsException.class)
    public final ResponseEntity<String> handleMultipleDealsException(MultipleDealsException resourceNotFoundException) {
        return new ResponseEntity<>(resourceNotFoundException.getMessage(), HttpStatus.BAD_REQUEST);
    }

}
