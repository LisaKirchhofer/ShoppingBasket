package com.bitside.shoppingbasket.adapter.persistence.model;

import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data
@Builder(toBuilder = true)
public class ShoppingBasketEntity {

    private Integer id;
    private List<ProductEntity> products;
    private List<DealEntity> deals;
}
