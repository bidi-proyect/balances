package com.bidi.balances.service.impl;


import com.bidi.balances.dto.CreateBalanceRequest;
import com.bidi.balances.entity.Balance;
import com.bidi.balances.repository.BalanceRepository;
import com.bidi.balances.service.CreateBalanceService;
import com.bidi.balances.utils.MessageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class CreateBalanceImpl implements CreateBalanceService {

    private final BalanceRepository balanceRepository;

    @Override
    public MessageResponse createBalance(CreateBalanceRequest createBalanceRequest) {
        Balance balance = balanceRepository.findBalanceByPhoneNumber(createBalanceRequest.getPhoneNumber());
        if(balance != null) {
            return new MessageResponse("Balance Exists.");
        }
        balance = requestToBalance(createBalanceRequest);
        balanceRepository.save(balance);
        return new MessageResponse("Balance created successfully.");
    }

    public Balance requestToBalance(CreateBalanceRequest createBalanceRequest) {
        Balance balance = new Balance();
        balance.setBalance(createBalanceRequest.getBalance());
        balance.setCreationDate(LocalDateTime.now());
        balance.setPhoneNumber(createBalanceRequest.getPhoneNumber());
        return balance;
    }
}
