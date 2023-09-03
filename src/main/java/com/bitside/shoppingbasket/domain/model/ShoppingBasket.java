package com.bitside.shoppingbasket.domain.model;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
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
            case TEN_PERCENT_OFF -> {
                var regularPrice = calculatePrice(product, amount);
                yield regularPrice.multiply(BigDecimal.valueOf(0.9), new MathContext(3, RoundingMode.FLOOR));
            }
            case BUY_ONE_GET_ONE_FREE -> {
                var discountPrice = calculatePrice(product, getPromotionAmount(amount));
                yield isEven(amount) ? discountPrice : discountPrice.add(product.getPrice());
            }
        }).orElseGet(() -> calculatePrice(product, amount));
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
        return amount.divide(BigDecimal.valueOf(2), new MathContext(1, RoundingMode.FLOOR));
    }

    private static boolean isEven(BigDecimal amount) {
        return BigDecimal.ZERO.compareTo(amount.remainder(BigDecimal.valueOf(2))) == 0;
    }

}
