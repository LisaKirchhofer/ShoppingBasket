package com.bitside.shoppingbasket.adapter.persistence;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import com.bitside.shoppingbasket.adapter.persistence.factory.ProductEntityFactory;
import com.bitside.shoppingbasket.adapter.persistence.mapper.ProductEntityMapper;
import com.bitside.shoppingbasket.adapter.persistence.model.ProductEntity;
import com.bitside.shoppingbasket.domain.application.port.spi.GetProductById;
import com.bitside.shoppingbasket.domain.application.port.spi.GetProducts;
import com.bitside.shoppingbasket.domain.model.Product;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class ProductStore implements GetProducts, GetProductById {

    private static final List<ProductEntity> products = List.of(
            ProductEntityFactory.of(0, "Bread", new BigDecimal("3.5")),
            ProductEntityFactory.of(1, "Milk", new BigDecimal("1.99")),
            ProductEntityFactory.of(2, "Orange Juice", new BigDecimal("1.24")),
            ProductEntityFactory.of(3, "Cookies", new BigDecimal("3.19"))
    );

    private final ProductEntityMapper productEntityMapper;

    @Override
    public List<Product> getProducts() {
        return productEntityMapper.toProducts(products);
    }

    @Override
    public Optional<Product> getProductById(Integer id) {
        return products.stream()
                .filter(it -> it.getId().equals(id))
                .findFirst()
                .map(productEntityMapper::toProduct);
    }

}
