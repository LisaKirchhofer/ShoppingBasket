package com.bitside.shoppingbasket.adapter.persistence.mapper;

import java.util.List;

import com.bitside.shoppingbasket.adapter.persistence.model.DealTypeEntity;
import com.bitside.shoppingbasket.domain.model.DealType;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DealTypeEntityMapper {

    List<DealType> toDealTypes(List<DealTypeEntity> dealTypes);
}
