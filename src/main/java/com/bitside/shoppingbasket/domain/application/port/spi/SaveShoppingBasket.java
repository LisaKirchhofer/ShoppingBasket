package com.bitside.shoppingbasket.domain.application.port.spi;

import com.bitside.shoppingbasket.domain.model.ShoppingBasket;

public interface SaveShoppingBasket {

    ShoppingBasket saveShoppingBasket(ShoppingBasket shoppingBasket);
}
