package com.bidi.balances.service.impl;

import com.bidi.balances.dto.CreateBalanceResponse;
import com.bidi.balances.entity.Balance;
import com.bidi.balances.repository.BalanceRepository;
import com.bidi.balances.service.GetBalanceByPhoneNumberService;
import com.bidi.balances.utils.BalanceException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetBalanceByPhoneNumberImpl implements GetBalanceByPhoneNumberService {

    private final BalanceRepository balanceRepository;
    @Override
    public CreateBalanceResponse getBalances(String phoneNumber) throws Exception {
        Balance balance = balanceRepository.findBalanceByPhoneNumber(phoneNumber);

        if (balance == null) {
            throw new BalanceException("Balance not exists.", HttpStatus.BAD_REQUEST);
        }

        return balanceToResponse(balance);
    }

    public CreateBalanceResponse balanceToResponse(Balance balance) {
        CreateBalanceResponse response = new CreateBalanceResponse();

        response.setIdBalance(balance.getIdBalance());
        response.setIdUser(balance.getIdUser());
        response.setBalance(balance.getBalance());
        response.setPhoneNumber(balance.getPhoneNumber());
        response.setCreationDate(balance.getCreationDate());
        response.setUpdateDate(balance.getUpdateDate());
        return response;
    }
}
