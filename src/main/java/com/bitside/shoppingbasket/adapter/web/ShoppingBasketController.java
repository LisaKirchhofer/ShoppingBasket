package com.bitside.shoppingbasket.adapter.web;

import java.math.BigDecimal;
import java.util.List;

import com.bitside.shoppingbasket.adapter.web.mapper.DealTypeApiMapper;
import com.bitside.shoppingbasket.adapter.web.mapper.ShoppingBasketApiMapper;
import com.bitside.shoppingbasket.api.ShoppingBasketsApi;
import com.bitside.shoppingbasket.domain.application.port.api.AddDealToShoppingBasketUseCase;
import com.bitside.shoppingbasket.domain.application.port.api.AddProductToShoppingBasketUseCase;
import com.bitside.shoppingbasket.domain.application.port.api.CreateShoppingBasketUseCase;
import com.bitside.shoppingbasket.domain.application.port.api.GetShoppingBasketTotalPriceUseCase;
import com.bitside.shoppingbasket.domain.application.port.api.GetShoppingBasketsUseCase;
import com.bitside.shoppingbasket.domain.model.AddDealCommand;
import com.bitside.shoppingbasket.domain.model.AddProductCommand;
import com.bitside.shoppingbasket.model.DealRequest;
import com.bitside.shoppingbasket.model.ProductRequest;
import com.bitside.shoppingbasket.model.ShoppingBasket;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor
public class ShoppingBasketController implements ShoppingBasketsApi {

    private final GetShoppingBasketsUseCase getShoppingBasketsUseCase;
    private final CreateShoppingBasketUseCase createShoppingBasketUseCase;
    private final AddProductToShoppingBasketUseCase addProductToShoppingBasketUseCase;
    private final AddDealToShoppingBasketUseCase addDealToShoppingBasketUseCase;
    private final GetShoppingBasketTotalPriceUseCase getShoppingBasketTotalPriceUseCase;
    private final ShoppingBasketApiMapper shoppingBasketApiMapper;
    private final DealTypeApiMapper dealTypeApiMapper;

    @Override
    public ResponseEntity<List<ShoppingBasket>> getShoppingBaskets() {
        return ResponseEntity.ok(shoppingBasketApiMapper.toApiShoppingBaskets(getShoppingBasketsUseCase.getShoppingBaskets()));
    }

    @Override
    public ResponseEntity<ShoppingBasket> createShoppingBasket() {
        return ResponseEntity.ok(shoppingBasketApiMapper.toApiShoppingBasket(createShoppingBasketUseCase.createShoppingBasket()));
    }


    @Override
    public ResponseEntity<ShoppingBasket> addProductToShoppingBasket(Integer id, ProductRequest productRequest) {
        return ResponseEntity.ok(shoppingBasketApiMapper.toApiShoppingBasket(addProductToShoppingBasketUseCase.addProductToShoppingBasket(createAddProductCommand(id, productRequest))));
    }

    @Override
    public ResponseEntity<ShoppingBasket> addDealToShoppingBasket(Integer id, DealRequest dealRequest) {
        return ResponseEntity.ok(shoppingBasketApiMapper.toApiShoppingBasket(addDealToShoppingBasketUseCase.addDealToShoppingBasket(createAddDealProductCommand(id, dealRequest))));
    }

    @Override
    public ResponseEntity<BigDecimal> getShoppingBasketTotalPrice(Integer id) {
        return ResponseEntity.ok(getShoppingBasketTotalPriceUseCase.getShoppingBasketTotalPrice(id));
    }

    private AddProductCommand createAddProductCommand(Integer id, ProductRequest productRequest) {
        return AddProductCommand.builder()
                .shoppingBasketId(id)
                .productId(productRequest.getProductId())
                .build();
    }

    private AddDealCommand createAddDealProductCommand(Integer id, DealRequest dealRequest) {
        return AddDealCommand.builder()
                .shoppingBasketId(id)
                .productId(dealRequest.getProductId())
                .type(dealTypeApiMapper.toDealType(dealRequest.getType()))
                .build();
    }

}
