package com.bitside.shoppingbasket.adapter.web.mapper;

import java.util.List;

import com.bitside.shoppingbasket.domain.model.DealType;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DealTypeApiMapper {

    List<com.bitside.shoppingbasket.model.DealType> toApiDealTypes(List<DealType> dealTypes);

    DealType toDealType(com.bitside.shoppingbasket.model.DealType dealType);
}
