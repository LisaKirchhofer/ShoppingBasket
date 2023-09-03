package com.bitside.shoppingbasket.domain.application.service.provider;

import java.util.List;

import com.bitside.shoppingbasket.domain.model.DealType;

public final class DealTypeTestDataProvider {

    public static List<DealType> getDealTypes() {
        return List.of(
                DealType.TEN_PERCENT_OFF,
                DealType.BUY_ONE_GET_ONE_FREE
        );
    }

}
