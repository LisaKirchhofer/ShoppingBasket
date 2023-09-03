package com.bitside.shoppingbasket.adapter.web;

import java.util.List;

import com.bitside.shoppingbasket.adapter.web.mapper.ProductApiMapper;
import com.bitside.shoppingbasket.api.ProductsApi;
import com.bitside.shoppingbasket.domain.application.port.api.GetProductsUseCase;
import com.bitside.shoppingbasket.model.Product;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor
public class ProductController implements ProductsApi {

    private final GetProductsUseCase getProductsUseCase;
    private final ProductApiMapper productApiMapper;

    @Override
    public ResponseEntity<List<Product>> getProducts() {
        return ResponseEntity.ok(productApiMapper.toApiProducts(getProductsUseCase.getProducts()));
    }

}
