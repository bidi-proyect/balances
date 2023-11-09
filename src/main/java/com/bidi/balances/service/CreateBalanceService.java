package com.bidi.balances.service;

import com.bidi.balances.dto.CreateBalanceRequest;
import com.bidi.balances.utils.MessageResponse;
import org.springframework.stereotype.Service;

@Service
public interface CreateBalanceService {
    MessageResponse createBalance(CreateBalanceRequest createBalanceRequest);

}
