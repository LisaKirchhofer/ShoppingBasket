package com.bitside.shoppingbasket.domain.model;

import static com.bitside.shoppingbasket.domain.properties.ShoppingBasketProperties.BUY_ONE_GET_ONE_FREE_VALUE;
import static com.bitside.shoppingbasket.domain.properties.ShoppingBasketProperties.PRECISION_ONE;
import static com.bitside.shoppingbasket.domain.properties.ShoppingBasketProperties.PRECISION_TWO;
import static com.bitside.shoppingbasket.domain.properties.ShoppingBasketProperties.TEN_PERCENT_OFF_VALUE;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.bitside.shoppingbasket.domain.application.port.exception.MultipleDealsException;

import lombok.Builder;
import lombok.Data;

@Data
@Builder(toBuilder = true)
public class ShoppingBasket {

    private Integer id;
    private List<Product> products;

    private List<Deal> deals;

    public void addProduct(Product product) {
        this.products.add(product);
    }

    public void addDeal(Deal deal) {
        if (this.deals.stream().anyMatch(it -> it.getProductId().equals(deal.getProductId()))) {
            throw new MultipleDealsException();
        }

        this.deals.add(deal);
    }

    public BigDecimal getTotalPrice() {
        var productIdAmountMap = this.products.stream()
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

        return productIdAmountMap.entrySet().stream()
                .map((Map.Entry<Product, Long> entry) ->
                        calculatePricePerProduct(entry.getKey(), BigDecimal.valueOf(entry.getValue())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private BigDecimal calculatePricePerProduct(Product product, BigDecimal amount) {
        return getDealType(product).map(it -> switch (it) {
            case TEN_PERCENT_OFF -> calculateTenPercentOffDeal(product, amount);
            case BUY_ONE_GET_ONE_FREE -> calculateBuyOneGetOneFreeDeal(product, amount);
        }).orElseGet(() -> calculatePrice(product, amount));
    }

    private static BigDecimal calculateBuyOneGetOneFreeDeal(Product product, BigDecimal amount) {
        var discountPrice = calculatePrice(product, getPromotionAmount(amount));
        return isEven(amount) ? discountPrice : discountPrice.add(product.getPrice());
    }

    private static BigDecimal calculateTenPercentOffDeal(Product product, BigDecimal amount) {
        var regularPrice = calculatePrice(product, amount);
        return regularPrice.multiply(TEN_PERCENT_OFF_VALUE, PRECISION_TWO);
    }

    private Optional<DealType> getDealType(Product product) {
        return this.deals.stream()
                .filter(it -> product.getId().equals(it.getProductId()))
                .map(Deal::getType)
                .findFirst();
    }

    private static BigDecimal calculatePrice(Product product, BigDecimal amount) {
        return product.getPrice().multiply(amount);
    }

    private static BigDecimal getPromotionAmount(BigDecimal amount) {
        return amount.divide(BUY_ONE_GET_ONE_FREE_VALUE, PRECISION_ONE);
    }

    private static boolean isEven(BigDecimal amount) {
        return BigDecimal.ZERO.compareTo(amount.remainder(BUY_ONE_GET_ONE_FREE_VALUE)) == 0;
    }

}
