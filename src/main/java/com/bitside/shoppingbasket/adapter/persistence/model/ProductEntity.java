package com.bitside.shoppingbasket.adapter.persistence.model;

import java.math.BigDecimal;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductEntity {

    private Integer id;
    private String name;
    private BigDecimal price;
}
