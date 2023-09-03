package com.bitside.shoppingbasket.domain.application.port.exception;

public class ShoppingBasketNotFoundException extends ResourceNotFoundException {

    public ShoppingBasketNotFoundException(Integer id) {
        super("Shopping Basket with id " + id + " could not be found");
    }
}
