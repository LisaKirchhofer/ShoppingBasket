package com.bitside.shoppingbasket.domain.application.service.provider;

import static com.bitside.shoppingbasket.domain.model.DealType.BUY_ONE_GET_ONE_FREE;
import static com.bitside.shoppingbasket.domain.model.DealType.TEN_PERCENT_OFF;

import com.bitside.shoppingbasket.domain.model.Deal;
import com.bitside.shoppingbasket.domain.model.DealType;

public final class DealTestDataProvider {

    public static final Deal TEN_PERCENT_OFF_BREAD_DEAL = createDeal(TEN_PERCENT_OFF, 0);
    public static final Deal BUY_ONE_GET_ONE_FREE_BREAD_DEAL = createDeal(BUY_ONE_GET_ONE_FREE, 0);
    public static final Deal TEN_PERCENT_OFF_MILK_DEAL = createDeal(TEN_PERCENT_OFF, 1);

    private static Deal createDeal(DealType type, Integer productId) {
        return Deal.builder()
                .type(type)
                .productId(productId)
                .build();
    }

}
