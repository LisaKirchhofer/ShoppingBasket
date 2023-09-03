package com.bitside.shoppingbasket.domain.application.port.spi;

import java.util.List;

import com.bitside.shoppingbasket.domain.model.DealType;

public interface GetDealTypes {

    List<DealType> getDealTypes();
}
