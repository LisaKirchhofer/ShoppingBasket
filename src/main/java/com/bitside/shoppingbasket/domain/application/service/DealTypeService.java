package com.bitside.shoppingbasket.domain.application.service;

import java.util.List;

import com.bitside.shoppingbasket.domain.application.port.api.GetDealTypesUseCase;
import com.bitside.shoppingbasket.domain.application.port.spi.GetDealTypes;
import com.bitside.shoppingbasket.domain.model.DealType;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class DealTypeService implements GetDealTypesUseCase {

    private GetDealTypes getDealTypes;

    @Override
    public List<DealType> getDealTypes() {
        return getDealTypes.getDealTypes();
    }

}
