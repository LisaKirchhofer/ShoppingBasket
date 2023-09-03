package com.bitside.shoppingbasket.domain.application.port.exception;

public class ProductNotFoundException extends ResourceNotFoundException {

    public ProductNotFoundException(Integer id) {
        super("Product with id " + id + " could not be found");
    }
}
