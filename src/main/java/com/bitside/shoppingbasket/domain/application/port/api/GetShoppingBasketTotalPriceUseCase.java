package com.bitside.shoppingbasket.domain.application.port.api;

import java.math.BigDecimal;

public interface GetShoppingBasketTotalPriceUseCase {

    BigDecimal getShoppingBasketTotalPrice(Integer shoppingBasketId);
}
