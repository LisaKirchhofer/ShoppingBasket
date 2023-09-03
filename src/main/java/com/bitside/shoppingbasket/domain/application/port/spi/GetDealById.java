package com.bitside.shoppingbasket.domain.application.port.spi;

import java.util.Optional;

import com.bitside.shoppingbasket.domain.model.Deal;

public interface GetDealById {

    Optional<Deal> getDealById(Integer id);
}
