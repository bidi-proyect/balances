package com.bidi.balances.dto;

import lombok.Data;

@Data
public class UpdateProducerBalanceRequest {
    private long amount;
    private String phoneProducer;
    private int action;
}
