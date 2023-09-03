package com.bitside.shoppingbasket.adapter.web.mapper;

import java.util.List;

import com.bitside.shoppingbasket.domain.model.ShoppingBasket;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ShoppingBasketApiMapper {

    List<com.bitside.shoppingbasket.model.ShoppingBasket> toApiShoppingBaskets(List<ShoppingBasket> shoppingBaskets);

    com.bitside.shoppingbasket.model.ShoppingBasket toApiShoppingBasket(ShoppingBasket shoppingBasket);
}
