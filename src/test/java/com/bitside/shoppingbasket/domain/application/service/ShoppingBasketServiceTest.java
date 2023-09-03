package com.bitside.shoppingbasket.domain.application.service;

import static com.bitside.shoppingbasket.domain.application.service.provider.DealTestDataProvider.TEN_PERCENT_OFF_BREAD_DEAL;
import static com.bitside.shoppingbasket.domain.application.service.provider.DealTestDataProvider.TEN_PERCENT_OFF_MILK_DEAL;
import static com.bitside.shoppingbasket.domain.application.service.provider.ProductTestDataProvider.BREAD;
import static com.bitside.shoppingbasket.domain.application.service.provider.ProductTestDataProvider.MILK;
import static com.bitside.shoppingbasket.domain.application.service.provider.ShoppingBasketTestDataProvider.getNewlyCreatedShoppingBasket;
import static com.bitside.shoppingbasket.domain.application.service.provider.ShoppingBasketTestDataProvider.getShoppingBasketMixedProductsAndDeals;
import static com.bitside.shoppingbasket.domain.application.service.provider.ShoppingBasketTestDataProvider.getShoppingBasketWithBreadAndNoDeals;
import static com.bitside.shoppingbasket.domain.application.service.provider.ShoppingBasketTestDataProvider.getShoppingBasketWithBreadAndTenPercentOffDeal;
import static com.bitside.shoppingbasket.domain.application.service.provider.ShoppingBasketTestDataProvider.getShoppingBasketWithThreeBreadsAndBuyOneGetOneFreeDeal;
import static com.bitside.shoppingbasket.domain.application.service.provider.ShoppingBasketTestDataProvider.getShoppingBasketWithTwoBreadsAndBuyOneGetOneFreeDeal;
import static com.bitside.shoppingbasket.domain.model.DealType.BUY_ONE_GET_ONE_FREE;
import static com.bitside.shoppingbasket.domain.model.DealType.TEN_PERCENT_OFF;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import com.bitside.shoppingbasket.domain.application.port.api.GetProductByIdUseCase;
import com.bitside.shoppingbasket.domain.application.port.exception.MultipleDealsException;
import com.bitside.shoppingbasket.domain.application.port.exception.ProductNotFoundException;
import com.bitside.shoppingbasket.domain.application.port.exception.ShoppingBasketNotFoundException;
import com.bitside.shoppingbasket.domain.application.port.spi.CreateShoppingBasket;
import com.bitside.shoppingbasket.domain.application.port.spi.GetShoppingBasketById;
import com.bitside.shoppingbasket.domain.application.port.spi.GetShoppingBaskets;
import com.bitside.shoppingbasket.domain.application.port.spi.SaveShoppingBasket;
import com.bitside.shoppingbasket.domain.application.service.provider.ShoppingBasketTestDataProvider;
import com.bitside.shoppingbasket.domain.model.AddDealCommand;
import com.bitside.shoppingbasket.domain.model.AddProductCommand;
import com.bitside.shoppingbasket.domain.model.ShoppingBasket;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class ShoppingBasketServiceTest {

    private ShoppingBasketService shoppingBasketService;
    private GetShoppingBaskets getShoppingBaskets;
    private GetShoppingBasketById getShoppingBasketById;
    private CreateShoppingBasket createShoppingBasket;
    private SaveShoppingBasket saveShoppingBasket;
    private GetProductByIdUseCase getProductByIdUseCase;

    private static Stream<Arguments> shoppingBasketsAndPrices() {
        return Stream.of(
                Arguments.of(getNewlyCreatedShoppingBasket(), BigDecimal.ZERO),
                Arguments.of(getShoppingBasketWithBreadAndNoDeals(), new BigDecimal("3.5")),
                Arguments.of(getShoppingBasketWithBreadAndTenPercentOffDeal(), new BigDecimal("3.15")),
                Arguments.of(getShoppingBasketWithTwoBreadsAndBuyOneGetOneFreeDeal(), new BigDecimal("3.5")),
                Arguments.of(getShoppingBasketWithThreeBreadsAndBuyOneGetOneFreeDeal(), new BigDecimal("7.0")),
                Arguments.of(getShoppingBasketMixedProductsAndDeals(), new BigDecimal("8.79"))

        );
    }

    @BeforeEach
    void setUp() {
        this.getShoppingBaskets = mock(GetShoppingBaskets.class);
        this.getShoppingBasketById = mock(GetShoppingBasketById.class);
        this.createShoppingBasket = mock(CreateShoppingBasket.class);
        this.saveShoppingBasket = mock(SaveShoppingBasket.class);
        this.getProductByIdUseCase = mock(GetProductByIdUseCase.class);
        this.shoppingBasketService = new ShoppingBasketService(
                getShoppingBaskets,
                getShoppingBasketById,
                createShoppingBasket,
                saveShoppingBasket,
                getProductByIdUseCase
        );
    }

    @Test
    public void getShoppingBasketsReturnsShoppingBaskets() {
        when(getShoppingBaskets.getShoppingBaskets()).thenReturn(ShoppingBasketTestDataProvider.getShoppingBaskets());

        var shoppingBaskets = shoppingBasketService.getShoppingBaskets();
        assertThat(shoppingBaskets.size()).isEqualTo(6);
        assertThat(shoppingBaskets).containsExactlyInAnyOrder(
                getNewlyCreatedShoppingBasket(),
                getShoppingBasketWithBreadAndNoDeals(),
                getShoppingBasketWithBreadAndTenPercentOffDeal(),
                getShoppingBasketWithTwoBreadsAndBuyOneGetOneFreeDeal(),
                getShoppingBasketWithThreeBreadsAndBuyOneGetOneFreeDeal(),
                getShoppingBasketMixedProductsAndDeals()
        );
    }

    @Test
    public void getShoppingBasketsReturnsEmptyList() {
        when(getShoppingBaskets.getShoppingBaskets()).thenReturn(Collections.emptyList());

        var shoppingBaskets = shoppingBasketService.getShoppingBaskets();
        assertTrue(shoppingBaskets.isEmpty());
    }

    @Test
    public void createShoppingBasketCreatesShoppingBasket() {
        when(createShoppingBasket.createShoppingBasket()).thenReturn(getNewlyCreatedShoppingBasket());

        var shoppingBasket = shoppingBasketService.createShoppingBasket();
        assertNotNull(shoppingBasket);
        assertEquals(getNewlyCreatedShoppingBasket(), shoppingBasket);

        var products = shoppingBasket.getProducts();
        assertNotNull(products);
        assertTrue(products.isEmpty());

        var deals = shoppingBasket.getDeals();
        assertNotNull(deals);
        assertTrue(deals.isEmpty());
    }

    @Test
    public void addProductToShoppingBasketAddsSingleProduct() {
        when(getShoppingBasketById.getShoppingBasketById(0)).thenReturn(Optional.of(getNewlyCreatedShoppingBasket()));
        when(getProductByIdUseCase.getProductById(0)).thenReturn(Optional.of(BREAD));
        when(saveShoppingBasket.saveShoppingBasket(any())).thenReturn(getNewlyCreatedShoppingBasket().toBuilder().products(List.of(BREAD)).build());

        var shoppingBasket =
                shoppingBasketService.addProductToShoppingBasket(AddProductCommand.builder().shoppingBasketId(0).productId(0).build());
        assertNotNull(shoppingBasket);
        assertThat(shoppingBasket.getId()).isEqualTo(0);

        var products = shoppingBasket.getProducts();
        assertNotNull(products);
        assertThat(products.size()).isEqualTo(1);
        assertThat(products).containsExactlyInAnyOrder(BREAD);

        var deals = shoppingBasket.getDeals();
        assertNotNull(deals);
        assertTrue(deals.isEmpty());
    }

    @Test
    public void addProductToShoppingBasketAddsMultipleProducts() {
        when(getShoppingBasketById.getShoppingBasketById(1)).thenReturn(Optional.of(getShoppingBasketWithBreadAndNoDeals()));
        when(getProductByIdUseCase.getProductById(1)).thenReturn(Optional.of(MILK));
        when(saveShoppingBasket.saveShoppingBasket(any())).thenReturn(getShoppingBasketWithBreadAndNoDeals().toBuilder().products(List.of(BREAD, MILK)).build());

        var shoppingBasket =
                shoppingBasketService.addProductToShoppingBasket(AddProductCommand.builder().shoppingBasketId(1).productId(1).build());
        assertNotNull(shoppingBasket);
        assertThat(shoppingBasket.getId()).isEqualTo(1);

        var products = shoppingBasket.getProducts();
        assertNotNull(products);
        assertThat(products.size()).isEqualTo(2);
        assertThat(products).containsExactlyInAnyOrder(BREAD, MILK);

        var deals = shoppingBasket.getDeals();
        assertNotNull(deals);
        assertTrue(deals.isEmpty());
    }

    @Test
    public void addProductToShoppingBasketThrowsShoppingBasketNotFoundException() {
        when(getShoppingBasketById.getShoppingBasketById(6)).thenReturn(Optional.empty());
        when(getProductByIdUseCase.getProductById(0)).thenReturn(Optional.of(BREAD));

        assertThrows(
                ShoppingBasketNotFoundException.class,
                () -> shoppingBasketService.addProductToShoppingBasket(AddProductCommand.builder().shoppingBasketId(6).productId(0).build())
        );
    }

    @Test
    public void addProductToShoppingBasketThrowsProductNotFoundException() {
        when(getShoppingBasketById.getShoppingBasketById(0)).thenReturn(Optional.of(getNewlyCreatedShoppingBasket()));
        when(getProductByIdUseCase.getProductById(4)).thenReturn(Optional.empty());

        assertThrows(
                ProductNotFoundException.class,
                () -> shoppingBasketService.addProductToShoppingBasket(AddProductCommand.builder().shoppingBasketId(0).productId(4).build())
        );
    }

    @Test
    public void addDealToShoppingBasketAddsSingleDeal() {
        when(getShoppingBasketById.getShoppingBasketById(0)).thenReturn(Optional.of(getNewlyCreatedShoppingBasket()));
        when(getProductByIdUseCase.getProductById(0)).thenReturn(Optional.of(BREAD));
        when(saveShoppingBasket.saveShoppingBasket(any())).thenReturn(getNewlyCreatedShoppingBasket().toBuilder().deals(List.of(TEN_PERCENT_OFF_BREAD_DEAL)).build());

        var shoppingBasket =
                shoppingBasketService.addDealToShoppingBasket(AddDealCommand.builder().shoppingBasketId(0).type(TEN_PERCENT_OFF).productId(0).build());
        assertNotNull(shoppingBasket);
        assertThat(shoppingBasket.getId()).isEqualTo(0);

        var products = shoppingBasket.getProducts();
        assertNotNull(products);
        assertTrue(products.isEmpty());

        var deals = shoppingBasket.getDeals();
        assertNotNull(deals);
        assertThat(deals.size()).isEqualTo(1);
        assertThat(deals).containsExactlyInAnyOrder(TEN_PERCENT_OFF_BREAD_DEAL);
    }

    @Test
    public void addDealToShoppingBasketAddsMultipleDeals() {
        when(getShoppingBasketById.getShoppingBasketById(2)).thenReturn(Optional.of(getShoppingBasketWithBreadAndTenPercentOffDeal()));
        when(getProductByIdUseCase.getProductById(1)).thenReturn(Optional.of(MILK));
        when(saveShoppingBasket.saveShoppingBasket(any())).thenReturn(getShoppingBasketWithBreadAndTenPercentOffDeal().toBuilder().deals(List.of(TEN_PERCENT_OFF_BREAD_DEAL, TEN_PERCENT_OFF_MILK_DEAL)).build());

        var shoppingBasket =
                shoppingBasketService.addDealToShoppingBasket(AddDealCommand.builder().shoppingBasketId(2).type(TEN_PERCENT_OFF).productId(1).build());
        assertNotNull(shoppingBasket);
        assertThat(shoppingBasket.getId()).isEqualTo(2);

        var products = shoppingBasket.getProducts();
        assertNotNull(products);
        assertThat(products.size()).isEqualTo(1);
        assertThat(products).containsExactlyInAnyOrder(BREAD);

        var deals = shoppingBasket.getDeals();
        assertNotNull(deals);
        assertThat(deals.size()).isEqualTo(2);
        assertThat(deals).containsExactlyInAnyOrder(TEN_PERCENT_OFF_BREAD_DEAL, TEN_PERCENT_OFF_MILK_DEAL);
    }

    @Test
    public void addDealToShoppingBasketThrowsShoppingBasketNotFoundException() {
        when(getShoppingBasketById.getShoppingBasketById(6)).thenReturn(Optional.empty());
        when(getProductByIdUseCase.getProductById(0)).thenReturn(Optional.of(BREAD));

        assertThrows(
                ShoppingBasketNotFoundException.class,
                () -> shoppingBasketService.addDealToShoppingBasket(AddDealCommand.builder().shoppingBasketId(6).type(TEN_PERCENT_OFF).productId(0).build())
        );
    }

    @Test
    public void addDealToShoppingBasketThrowsProductNotFoundException() {
        when(getShoppingBasketById.getShoppingBasketById(0)).thenReturn(Optional.of(getNewlyCreatedShoppingBasket()));
        when(getProductByIdUseCase.getProductById(4)).thenReturn(Optional.empty());

        assertThrows(
                ProductNotFoundException.class,
                () -> shoppingBasketService.addDealToShoppingBasket(AddDealCommand.builder().shoppingBasketId(0).productId(4).type(TEN_PERCENT_OFF).build())
        );
    }

    @Test
    public void addDealToShoppingBasketThrowsMultipleDealsException() {
        when(getShoppingBasketById.getShoppingBasketById(0)).thenReturn(Optional.of(getShoppingBasketWithBreadAndTenPercentOffDeal()));
        when(getProductByIdUseCase.getProductById(0)).thenReturn(Optional.of(BREAD));

        assertThrows(
                MultipleDealsException.class,
                () -> shoppingBasketService.addDealToShoppingBasket(AddDealCommand.builder().shoppingBasketId(0).productId(0).type(BUY_ONE_GET_ONE_FREE).build())
        );
    }

    @ParameterizedTest
    @MethodSource(value = "shoppingBasketsAndPrices")
    public void getShoppingBasketTotalPriceReturnsTotalPrice(ShoppingBasket shoppingBasket,
            BigDecimal expectedShoppingBasketTotalPrice) {
        when(getShoppingBasketById.getShoppingBasketById(shoppingBasket.getId())).thenReturn(Optional.of(shoppingBasket));

        var shoppingBasketTotalPrice = shoppingBasketService.getShoppingBasketTotalPrice(shoppingBasket.getId());

        assertThat(shoppingBasketTotalPrice).isEqualTo(expectedShoppingBasketTotalPrice);
    }

}