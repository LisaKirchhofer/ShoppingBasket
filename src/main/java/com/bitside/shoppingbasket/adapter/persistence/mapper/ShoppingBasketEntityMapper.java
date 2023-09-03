package com.bitside.shoppingbasket.adapter.persistence.mapper;

import java.util.List;

import com.bitside.shoppingbasket.adapter.persistence.model.ShoppingBasketEntity;
import com.bitside.shoppingbasket.domain.model.ShoppingBasket;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ShoppingBasketEntityMapper {

    List<ShoppingBasket> toShoppingBaskets(List<ShoppingBasketEntity> shoppingBaskets);

    ShoppingBasket toShoppingBasket(ShoppingBasketEntity shoppingBasket);

    ShoppingBasketEntity toShoppingBasketEntity(ShoppingBasket shoppingBasket);
}
