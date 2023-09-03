package com.bitside.shoppingbasket.domain.application.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.function.BiConsumer;

import com.bitside.shoppingbasket.domain.application.port.api.AddDealToShoppingBasketUseCase;
import com.bitside.shoppingbasket.domain.application.port.api.AddProductToShoppingBasketUseCase;
import com.bitside.shoppingbasket.domain.application.port.api.CreateShoppingBasketUseCase;
import com.bitside.shoppingbasket.domain.application.port.api.GetProductByIdUseCase;
import com.bitside.shoppingbasket.domain.application.port.api.GetShoppingBasketTotalPriceUseCase;
import com.bitside.shoppingbasket.domain.application.port.api.GetShoppingBasketsUseCase;
import com.bitside.shoppingbasket.domain.application.port.exception.ProductNotFoundException;
import com.bitside.shoppingbasket.domain.application.port.exception.ShoppingBasketNotFoundException;
import com.bitside.shoppingbasket.domain.application.port.spi.CreateShoppingBasket;
import com.bitside.shoppingbasket.domain.application.port.spi.GetShoppingBasketById;
import com.bitside.shoppingbasket.domain.application.port.spi.GetShoppingBaskets;
import com.bitside.shoppingbasket.domain.application.port.spi.SaveShoppingBasket;
import com.bitside.shoppingbasket.domain.model.AddDealCommand;
import com.bitside.shoppingbasket.domain.model.AddProductCommand;
import com.bitside.shoppingbasket.domain.model.Deal;
import com.bitside.shoppingbasket.domain.model.Product;
import com.bitside.shoppingbasket.domain.model.ShoppingBasket;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ShoppingBasketService implements GetShoppingBasketsUseCase, CreateShoppingBasketUseCase,
        AddProductToShoppingBasketUseCase, GetShoppingBasketTotalPriceUseCase, AddDealToShoppingBasketUseCase {

    private final GetShoppingBaskets getShoppingBaskets;
    private final GetShoppingBasketById getShoppingBasketById;
    private final CreateShoppingBasket createShoppingBasket;
    private final SaveShoppingBasket saveShoppingBasket;

    private final GetProductByIdUseCase getProductByIdUseCase;

    @Override
    public List<ShoppingBasket> getShoppingBaskets() {
        return getShoppingBaskets.getShoppingBaskets();
    }

    @Override
    public ShoppingBasket createShoppingBasket() {
        return createShoppingBasket.createShoppingBasket();
    }

    @Override
    public ShoppingBasket addProductToShoppingBasket(AddProductCommand addProductCommand) {
        return updateShoppingBasket(
                addProductCommand.getShoppingBasketId(),
                addProductCommand.getProductId(),
                ShoppingBasket::addProduct
        );
    }

    @Override
    public ShoppingBasket addDealToShoppingBasket(AddDealCommand addDealCommand) {
        return updateShoppingBasket(
                addDealCommand.getShoppingBasketId(),
                addDealCommand.getProductId(),
                (shoppingBasket, product) -> shoppingBasket.addDeal(createDeal(addDealCommand, product))
        );
    }

    @Override
    public BigDecimal getShoppingBasketTotalPrice(Integer shoppingBasketId) {
        return getShoppingBasket(shoppingBasketId).getTotalPrice();
    }

    private ShoppingBasket updateShoppingBasket(Integer shoppingBasketId, Integer productId,
            BiConsumer<ShoppingBasket, Product> updateFunction) {
        var shoppingBasket = getShoppingBasket(shoppingBasketId);
        var product = getProduct(productId);

        updateFunction.accept(shoppingBasket, product);

        return saveShoppingBasket.saveShoppingBasket(shoppingBasket);
    }

    private ShoppingBasket getShoppingBasket(Integer shoppingBasketId) {
        return getShoppingBasketById.getShoppingBasketById(shoppingBasketId).orElseThrow(() -> new ShoppingBasketNotFoundException(shoppingBasketId));
    }

    private Product getProduct(Integer productId) {
        return getProductByIdUseCase.getProductById(productId).orElseThrow(() -> new ProductNotFoundException(productId));
    }

    private Deal createDeal(AddDealCommand addDealCommand, Product product) {
        return Deal.builder()
                .type(addDealCommand.getType())
                .productId(product.getId())
                .build();
    }

}
