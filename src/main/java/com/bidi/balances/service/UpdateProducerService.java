package com.bidi.balances.service;

import com.bidi.balances.dto.UpdateProducerBalanceRequest;
import com.bidi.balances.utils.MessageResponse;
import org.springframework.stereotype.Service;

@Service
public interface UpdateProducerService {
    MessageResponse updateProducerBalance(UpdateProducerBalanceRequest updateProducerBalanceRequest, String idUser) throws Exception;
}
