package com.bidi.balances.service;

import com.bidi.balances.dto.CreateBalanceResponse;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public interface GetAllBalancesService {
    List<CreateBalanceResponse> getAllBalances();
}
