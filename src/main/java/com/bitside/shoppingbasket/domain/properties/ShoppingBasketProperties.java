package com.bitside.shoppingbasket.domain.properties;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

public class ShoppingBasketProperties {

    public static final BigDecimal TEN_PERCENT_OFF_VALUE = BigDecimal.valueOf(0.9);
    public static final BigDecimal BUY_ONE_GET_ONE_FREE_VALUE = BigDecimal.valueOf(2);
    public static final MathContext PRECISION_ONE = new MathContext(1, RoundingMode.FLOOR);
    public static final MathContext PRECISION_TWO = new MathContext(3, RoundingMode.FLOOR);

}
