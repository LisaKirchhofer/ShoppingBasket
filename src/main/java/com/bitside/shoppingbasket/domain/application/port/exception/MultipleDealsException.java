package com.bitside.shoppingbasket.domain.application.port.exception;

public class MultipleDealsException extends RuntimeException {

    public MultipleDealsException() {
        super("You must not define multiple deals for a single product!");
    }

}
