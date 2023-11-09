package com.bidi.balances.dto;

import lombok.Data;

@Data
public class UpdateProducerAndReceiverBalanceRequest {
    private long amount;
    private String phoneProducer;
    private String phoneReceiver;
}
