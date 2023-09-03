package com.bitside.shoppingbasket.domain.application.port.spi;

import java.util.List;

import com.bitside.shoppingbasket.domain.model.Product;

public interface GetProducts {

    List<Product> getProducts();
}
