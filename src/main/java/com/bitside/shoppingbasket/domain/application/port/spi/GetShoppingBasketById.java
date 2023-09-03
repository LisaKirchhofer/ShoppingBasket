package com.bitside.shoppingbasket.domain.application.port.spi;

import java.util.Optional;

import com.bitside.shoppingbasket.domain.model.ShoppingBasket;

public interface GetShoppingBasketById {

    Optional<ShoppingBasket> getShoppingBasketById(Integer id);
}
