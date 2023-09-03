package com.bitside.shoppingbasket.adapter.persistence.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DealEntity {

    private DealTypeEntity type;
    private Integer productId;
}
