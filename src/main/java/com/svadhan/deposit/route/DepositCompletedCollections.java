package com.svadhan.deposit.route;

import com.svadhan.deposit.model.CollectionDetailsResponse;
import com.svadhan.deposit.service.DepositService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class DepositCompletedCollections implements Function<Long, ResponseEntity<CollectionDetailsResponse>> {


    private final DepositService depositService;

    public DepositCompletedCollections(DepositService depositService) {
        this.depositService = depositService;
    }


    @Override
    public ResponseEntity<CollectionDetailsResponse> apply(Long agentId) {
        CollectionDetailsResponse collectionDetailsResponse = depositService.getCompletedCollections(agentId);
        return ResponseEntity.ok(collectionDetailsResponse);
    }
}
