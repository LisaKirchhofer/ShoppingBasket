package com.bitside.shoppingbasket.domain.application.port.api;

import com.bitside.shoppingbasket.domain.model.AddDealCommand;
import com.bitside.shoppingbasket.domain.model.ShoppingBasket;

public interface AddDealToShoppingBasketUseCase {

    ShoppingBasket addDealToShoppingBasket(AddDealCommand addDealCommand);
}
