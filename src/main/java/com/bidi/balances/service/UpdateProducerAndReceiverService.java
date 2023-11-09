package com.bidi.balances.service;


import com.bidi.balances.dto.UpdateProducerAndReceiverBalanceRequest;
import com.bidi.balances.utils.BalanceException;
import com.bidi.balances.utils.MessageResponse;

public interface UpdateProducerAndReceiverService {
    MessageResponse updateProducerAndReceiverBalance(UpdateProducerAndReceiverBalanceRequest updateProducerAndReceiverBalanceRequest, String idUser) throws BalanceException;
}
