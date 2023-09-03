package com.bitside.shoppingbasket.adapter.persistence.factory;

import java.math.BigDecimal;

import com.bitside.shoppingbasket.adapter.persistence.model.ProductEntity;

public final class ProductEntityFactory {

    public static ProductEntity of(Integer id, String name, BigDecimal price) {
        return ProductEntity.builder()
                .id(id)
                .name(name)
                .price(price)
                .build();
    }

}
