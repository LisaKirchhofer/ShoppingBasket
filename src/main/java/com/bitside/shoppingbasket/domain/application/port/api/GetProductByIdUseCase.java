package com.bitside.shoppingbasket.domain.application.port.api;

import java.util.Optional;

import com.bitside.shoppingbasket.domain.model.Product;

public interface GetProductByIdUseCase {

    Optional<Product> getProductById(Integer id);
}
