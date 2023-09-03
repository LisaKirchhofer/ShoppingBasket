package com.bitside.shoppingbasket.domain.application.port.spi;

import java.util.Optional;

import com.bitside.shoppingbasket.domain.model.Product;

public interface GetProductById {

    Optional<Product> getProductById(Integer id);
}
