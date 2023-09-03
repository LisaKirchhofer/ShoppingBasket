package com.bitside.shoppingbasket.adapter.web;

import java.util.List;

import com.bitside.shoppingbasket.adapter.web.mapper.DealTypeApiMapper;
import com.bitside.shoppingbasket.api.DealTypesApi;
import com.bitside.shoppingbasket.domain.application.port.api.GetDealTypesUseCase;
import com.bitside.shoppingbasket.model.DealType;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor
public class DealTypeController implements DealTypesApi {

    private final GetDealTypesUseCase getDealTypesUseCase;
    private final DealTypeApiMapper dealTypeApiMapper;

    @Override
    public ResponseEntity<List<DealType>> getDealTypes() {
        return ResponseEntity.ok(dealTypeApiMapper.toApiDealTypes(getDealTypesUseCase.getDealTypes()));
    }

}
