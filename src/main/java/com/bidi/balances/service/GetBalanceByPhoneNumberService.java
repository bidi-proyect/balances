package com.bidi.balances.service;

import com.bidi.balances.dto.CreateBalanceResponse;
import org.springframework.stereotype.Service;

@Service
public interface GetBalanceByPhoneNumberService {
    CreateBalanceResponse getBalances(String idUser) throws Exception;
}
