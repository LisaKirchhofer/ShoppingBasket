package com.bitside.shoppingbasket.domain.application.service.provider;

import static com.bitside.shoppingbasket.domain.application.service.provider.ProductTestDataProvider.BREAD;
import static com.bitside.shoppingbasket.domain.application.service.provider.ProductTestDataProvider.MILK;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.bitside.shoppingbasket.domain.model.Deal;
import com.bitside.shoppingbasket.domain.model.Product;
import com.bitside.shoppingbasket.domain.model.ShoppingBasket;

public final class ShoppingBasketTestDataProvider {

    public static final ShoppingBasket SHOPPING_BASKET_MIXED_PRODUCTS_AND_DEALS = createShoppingBasket(
            5,
            List.of(BREAD, BREAD, BREAD, MILK),
            List.of(DealTestDataProvider.BUY_ONE_GET_ONE_FREE_BREAD_DEAL,
                    DealTestDataProvider.TEN_PERCENT_OFF_MILK_DEAL)
    );

    public static List<ShoppingBasket> getShoppingBaskets() {
        return List.of(
                getNewlyCreatedShoppingBasket(),
                getShoppingBasketWithBreadAndNoDeals(),
                getShoppingBasketWithBreadAndTenPercentOffDeal(),
                getShoppingBasketWithTwoBreadsAndBuyOneGetOneFreeDeal(),
                getShoppingBasketWithThreeBreadsAndBuyOneGetOneFreeDeal(),
                getShoppingBasketMixedProductsAndDeals()
        );
    }

    public static ShoppingBasket getNewlyCreatedShoppingBasket() {
        return createShoppingBasket(
                0,
                Collections.emptyList(),
                Collections.emptyList()
        );
    }

    public static ShoppingBasket getShoppingBasketWithBreadAndNoDeals() {
        return createShoppingBasket(
                1,
                List.of(BREAD),
                Collections.emptyList()
        );
    }

    public static ShoppingBasket getShoppingBasketWithBreadAndTenPercentOffDeal() {
        return createShoppingBasket(
                2,
                List.of(BREAD),
                List.of(DealTestDataProvider.TEN_PERCENT_OFF_BREAD_DEAL)
        );
    }

    public static ShoppingBasket getShoppingBasketWithTwoBreadsAndBuyOneGetOneFreeDeal() {
        return createShoppingBasket(
                3,
                List.of(BREAD, BREAD),
                List.of(DealTestDataProvider.BUY_ONE_GET_ONE_FREE_BREAD_DEAL)
        );
    }

    public static ShoppingBasket getShoppingBasketWithThreeBreadsAndBuyOneGetOneFreeDeal() {
        return createShoppingBasket(
                4,
                List.of(BREAD, BREAD, BREAD),
                List.of(DealTestDataProvider.BUY_ONE_GET_ONE_FREE_BREAD_DEAL)
        );
    }

    public static ShoppingBasket getShoppingBasketMixedProductsAndDeals() {
        return createShoppingBasket(
                5,
                List.of(BREAD, BREAD, BREAD, MILK),
                List.of(DealTestDataProvider.BUY_ONE_GET_ONE_FREE_BREAD_DEAL,
                        DealTestDataProvider.TEN_PERCENT_OFF_MILK_DEAL)
        );
    }

    private static ShoppingBasket createShoppingBasket(Integer id, List<Product> products, List<Deal> deals) {
        return ShoppingBasket.builder()
                .id(id)
                .products(createMutableList(products))
                .deals(createMutableList(deals))
                .build();
    }

    private static <T> List<T> createMutableList(List<T> items) {
        var mutableList = new ArrayList<T>();
        mutableList.addAll(items);

        return mutableList;
    }

}
