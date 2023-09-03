package com.bitside.shoppingbasket.domain.application.service;

import java.util.List;
import java.util.Optional;

import com.bitside.shoppingbasket.domain.application.port.api.GetProductByIdUseCase;
import com.bitside.shoppingbasket.domain.application.port.api.GetProductsUseCase;
import com.bitside.shoppingbasket.domain.application.port.spi.GetProductById;
import com.bitside.shoppingbasket.domain.application.port.spi.GetProducts;
import com.bitside.shoppingbasket.domain.model.Product;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ProductService implements GetProductsUseCase, GetProductByIdUseCase {

    private final GetProducts getProducts;
    private final GetProductById getProductById;

    @Override
    public List<Product> getProducts() {
        return getProducts.getProducts();
    }

    @Override
    public Optional<Product> getProductById(Integer id) {
        return getProductById.getProductById(id);
    }

}
