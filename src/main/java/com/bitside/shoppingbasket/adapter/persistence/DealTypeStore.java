package com.bitside.shoppingbasket.adapter.persistence;

import java.util.List;

import com.bitside.shoppingbasket.adapter.persistence.mapper.DealTypeEntityMapper;
import com.bitside.shoppingbasket.adapter.persistence.model.DealTypeEntity;
import com.bitside.shoppingbasket.domain.application.port.spi.GetDealTypes;
import com.bitside.shoppingbasket.domain.model.DealType;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class DealTypeStore implements GetDealTypes {

    private static final List<DealTypeEntity> deals = List.of(
            DealTypeEntity.TEN_PERCENT_OFF,
            DealTypeEntity.BUY_ONE_GET_ONE_FREE
    );

    private final DealTypeEntityMapper dealTypeEntityMapper;

    @Override
    public List<DealType> getDealTypes() {
        return dealTypeEntityMapper.toDealTypes(deals);
    }

}
