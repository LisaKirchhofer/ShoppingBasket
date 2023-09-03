package com.bitside.shoppingbasket.domain.application.port.spi;

import java.util.List;

import com.bitside.shoppingbasket.domain.model.ShoppingBasket;

public interface GetShoppingBaskets {

    List<ShoppingBasket> getShoppingBaskets();
}
