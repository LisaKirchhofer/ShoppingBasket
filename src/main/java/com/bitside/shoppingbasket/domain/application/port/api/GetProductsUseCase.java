package com.bitside.shoppingbasket.domain.application.port.api;

import java.util.List;

import com.bitside.shoppingbasket.domain.model.Product;

public interface GetProductsUseCase {

    List<Product> getProducts();
}
