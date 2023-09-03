package com.bitside.shoppingbasket.domain.application.service.provider;

import java.math.BigDecimal;
import java.util.List;

import com.bitside.shoppingbasket.domain.model.Product;

public final class ProductTestDataProvider {

    public static final Product BREAD = createProduct(0, "Bread", "3.5");
    public static final Product MILK = createProduct(1, "Milk", "1.99");
    public static final Product ORANGE_JUICE = createProduct(2, "Orange Juice", "1.24");
    public static final Product COOKIES = createProduct(3, "Cookies", "3.19");

    public static List<Product> getProducts() {
        return List.of(
                BREAD,
                MILK,
                ORANGE_JUICE,
                COOKIES
        );
    }

    private static Product createProduct(Integer id, String name, String price) {
        return Product.builder()
                .id(id)
                .name(name)
                .price(new BigDecimal(price))
                .build();
    }

}
