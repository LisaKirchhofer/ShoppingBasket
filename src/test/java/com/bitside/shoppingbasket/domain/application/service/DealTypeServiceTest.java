package com.bitside.shoppingbasket.domain.application.service;

import static com.bitside.shoppingbasket.domain.model.DealType.BUY_ONE_GET_ONE_FREE;
import static com.bitside.shoppingbasket.domain.model.DealType.TEN_PERCENT_OFF;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Collections;

import com.bitside.shoppingbasket.domain.application.port.spi.GetDealTypes;
import com.bitside.shoppingbasket.domain.application.service.provider.DealTypeTestDataProvider;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DealTypeServiceTest {

    private DealTypeService dealTypeService;
    private GetDealTypes getDealTypes;

    @BeforeEach
    void setUp() {
        this.getDealTypes = mock(GetDealTypes.class);
        this.dealTypeService = new DealTypeService(getDealTypes);
    }

    @Test
    void getDealTypesReturnsDealTypes() {
        when(getDealTypes.getDealTypes()).thenReturn(DealTypeTestDataProvider.getDealTypes());

        var dealTypes = dealTypeService.getDealTypes();
        assertThat(dealTypes.size()).isEqualTo(2);
        assertThat(dealTypes).containsExactlyInAnyOrder(TEN_PERCENT_OFF, BUY_ONE_GET_ONE_FREE);
    }

    @Test
    void getDealTypesReturnsEmptyList() {
        when(getDealTypes.getDealTypes()).thenReturn(Collections.emptyList());

        var dealTypes = dealTypeService.getDealTypes();
        assertTrue(dealTypes.isEmpty());
    }

}