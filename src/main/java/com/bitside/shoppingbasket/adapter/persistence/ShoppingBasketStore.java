package com.bitside.shoppingbasket.adapter.persistence;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.bitside.shoppingbasket.adapter.persistence.factory.ShoppingBasketEntityFactory;
import com.bitside.shoppingbasket.adapter.persistence.mapper.ShoppingBasketEntityMapper;
import com.bitside.shoppingbasket.adapter.persistence.model.ShoppingBasketEntity;
import com.bitside.shoppingbasket.domain.application.port.exception.ShoppingBasketNotFoundException;
import com.bitside.shoppingbasket.domain.application.port.spi.CreateShoppingBasket;
import com.bitside.shoppingbasket.domain.application.port.spi.GetShoppingBasketById;
import com.bitside.shoppingbasket.domain.application.port.spi.GetShoppingBaskets;
import com.bitside.shoppingbasket.domain.application.port.spi.SaveShoppingBasket;
import com.bitside.shoppingbasket.domain.model.ShoppingBasket;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.ApplicationScope;

import lombok.AllArgsConstructor;

@Component
@ApplicationScope
@AllArgsConstructor
public class ShoppingBasketStore implements GetShoppingBaskets, GetShoppingBasketById, CreateShoppingBasket,
        SaveShoppingBasket {

    private static final List<ShoppingBasketEntity> shoppingBaskets = new ArrayList<>();

    private final ShoppingBasketEntityMapper shoppingBasketEntityMapper;

    @Override
    public List<ShoppingBasket> getShoppingBaskets() {
        return shoppingBasketEntityMapper.toShoppingBaskets(shoppingBaskets);
    }

    @Override
    public Optional<ShoppingBasket> getShoppingBasketById(Integer id) {
        return getShoppingBasketEntityById(id)
                .map(shoppingBasketEntityMapper::toShoppingBasket);
    }

    @Override
    public ShoppingBasket createShoppingBasket() {
        var shoppingBasket = ShoppingBasketEntityFactory.of(shoppingBaskets.size());
        shoppingBaskets.add(shoppingBasket);
        return shoppingBasketEntityMapper.toShoppingBasket(shoppingBasket);
    }

    @Override
    public ShoppingBasket saveShoppingBasket(ShoppingBasket shoppingBasket) {
        var newShoppingBasketEntity = shoppingBasketEntityMapper.toShoppingBasketEntity(shoppingBasket);
        shoppingBaskets.set(getIndexToReplace(shoppingBasket), newShoppingBasketEntity);

        return shoppingBasketEntityMapper.toShoppingBasket(newShoppingBasketEntity);
    }

    private Optional<ShoppingBasketEntity> getShoppingBasketEntityById(Integer id) {
        return shoppingBaskets.stream()
                .filter(it -> it.getId().equals(id))
                .findFirst();
    }

    private int getIndexToReplace(ShoppingBasket shoppingBasket) {
        var shoppingBasketEntity = getShoppingBasketEntityById(shoppingBasket.getId())
                .orElseThrow(() -> new ShoppingBasketNotFoundException(shoppingBasket.getId()));
        return shoppingBaskets.indexOf(shoppingBasketEntity);
    }

}
