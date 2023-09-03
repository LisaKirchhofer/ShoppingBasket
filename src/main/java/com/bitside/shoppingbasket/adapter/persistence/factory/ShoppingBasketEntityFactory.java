package com.bitside.shoppingbasket.adapter.persistence.factory;

import java.util.ArrayList;

import com.bitside.shoppingbasket.adapter.persistence.model.ShoppingBasketEntity;

public final class ShoppingBasketEntityFactory {

    public static ShoppingBasketEntity of(Integer id) {
        return ShoppingBasketEntity.builder()
                .id(id)
                .products(new ArrayList<>())
                .deals(new ArrayList<>())
                .build();
    }

}
