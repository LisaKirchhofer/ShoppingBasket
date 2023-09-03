package com.bitside.shoppingbasket.domain.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AddDealCommand {

    private Integer shoppingBasketId;
    private DealType type;
    private Integer productId;
}
