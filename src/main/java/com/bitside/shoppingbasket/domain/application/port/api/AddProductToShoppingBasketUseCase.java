package com.bitside.shoppingbasket.domain.application.port.api;

import com.bitside.shoppingbasket.domain.model.AddProductCommand;
import com.bitside.shoppingbasket.domain.model.ShoppingBasket;

public interface AddProductToShoppingBasketUseCase {

    ShoppingBasket addProductToShoppingBasket(AddProductCommand addProductCommand);
}
