package com.bitside.shoppingbasket.adapter.persistence.mapper;

import java.util.List;

import com.bitside.shoppingbasket.adapter.persistence.model.ProductEntity;
import com.bitside.shoppingbasket.domain.model.Product;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductEntityMapper {

    List<Product> toProducts(List<ProductEntity> products);

    Product toProduct(ProductEntity product);

    List<ProductEntity> toProductEntities(List<Product> products);
}
