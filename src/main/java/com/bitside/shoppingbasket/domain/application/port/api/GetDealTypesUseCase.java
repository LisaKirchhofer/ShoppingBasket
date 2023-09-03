package com.bitside.shoppingbasket.domain.application.port.api;

import java.util.List;

import com.bitside.shoppingbasket.domain.model.DealType;

public interface GetDealTypesUseCase {

    List<DealType> getDealTypes();
}
