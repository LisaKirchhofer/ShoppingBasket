package com.bitside.shoppingbasket.domain.application.service;

import static com.bitside.shoppingbasket.domain.application.service.provider.ProductTestDataProvider.BREAD;
import static com.bitside.shoppingbasket.domain.application.service.provider.ProductTestDataProvider.COOKIES;
import static com.bitside.shoppingbasket.domain.application.service.provider.ProductTestDataProvider.MILK;
import static com.bitside.shoppingbasket.domain.application.service.provider.ProductTestDataProvider.ORANGE_JUICE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.Optional;

import com.bitside.shoppingbasket.domain.application.port.spi.GetProductById;
import com.bitside.shoppingbasket.domain.application.port.spi.GetProducts;
import com.bitside.shoppingbasket.domain.application.service.provider.ProductTestDataProvider;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ProductServiceTest {

    private ProductService productService;
    private GetProducts getProducts;
    private GetProductById getProductById;

    @BeforeEach
    void setUp() {
        this.getProducts = mock(GetProducts.class);
        this.getProductById = mock(GetProductById.class);
        this.productService = new ProductService(getProducts, getProductById);
    }

    @Test
    void getProductsReturnsProducts() {
        when(getProducts.getProducts()).thenReturn(ProductTestDataProvider.getProducts());

        var products = productService.getProducts();
        assertThat(products.size()).isEqualTo(4);
        assertThat(products).containsExactlyInAnyOrder(BREAD, MILK, ORANGE_JUICE, COOKIES);
    }

    @Test
    void getProductsReturnsEmptyList() {
        when(getProducts.getProducts()).thenReturn(Collections.emptyList());

        var products = productService.getProducts();
        assertTrue(products.isEmpty());
    }

    @Test
    void getProductByIdReturnsProductOptional() {
        when(getProductById.getProductById(0)).thenReturn(Optional.of(BREAD));

        var product = productService.getProductById(0);
        assertTrue(product.isPresent());
        assertThat(product.get()).isEqualTo(BREAD);
    }

    @Test
    void getProductByIdReturnsEmptyOptional() {
        when(getProductById.getProductById(4)).thenReturn(Optional.empty());

        var product = productService.getProductById(4);
        assertTrue(product.isEmpty());
    }

}