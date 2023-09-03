package com.bitside.shoppingbasket.domain.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AddProductCommand {

    private Integer shoppingBasketId;
    private Integer productId;
}
