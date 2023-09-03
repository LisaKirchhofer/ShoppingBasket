package com.bitside.shoppingbasket.adapter.web.mapper;

import java.util.List;

import com.bitside.shoppingbasket.domain.model.Product;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductApiMapper {

    List<com.bitside.shoppingbasket.model.Product> toApiProducts(List<Product> product);
}
