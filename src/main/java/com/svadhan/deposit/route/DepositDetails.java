package com.svadhan.deposit.route;

import com.svadhan.deposit.model.DepositDetailsRequest;
import com.svadhan.deposit.model.DepositDetailsResponse;
import com.svadhan.deposit.service.DepositDetailsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Slf4j
@Component
public class DepositDetails implements Function<DepositDetailsRequest, DepositDetailsResponse> {
    @Autowired
    private DepositDetailsService depositDetailsService;
    @Override
    public DepositDetailsResponse apply(DepositDetailsRequest depositDetailsRequest){
        return depositDetailsService.saveDepositDetails(depositDetailsRequest);

    }
}
