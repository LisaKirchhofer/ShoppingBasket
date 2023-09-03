package com.bitside.shoppingbasket.domain.application.port.api;

import java.util.List;

import com.bitside.shoppingbasket.domain.model.ShoppingBasket;

public interface GetShoppingBasketsUseCase {

    List<ShoppingBasket> getShoppingBaskets();
}
