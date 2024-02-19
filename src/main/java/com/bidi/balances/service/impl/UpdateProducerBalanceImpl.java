package com.bidi.balances.service.impl;

import com.bidi.balances.dto.CreateBalanceRequest;
import com.bidi.balances.dto.UpdateProducerBalanceRequest;
import com.bidi.balances.entity.Balance;
import com.bidi.balances.repository.BalanceRepository;
import com.bidi.balances.service.CreateBalanceService;
import com.bidi.balances.service.UpdateProducerService;
import com.bidi.balances.utils.BalanceException;
import com.bidi.balances.utils.MessageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class UpdateProducerBalanceImpl implements UpdateProducerService {
    private final BalanceRepository balanceRepository;
    private final CreateBalanceService createBalanceService;

    @Override
    public MessageResponse updateProducerBalance(UpdateProducerBalanceRequest request, String idUser) {
        Balance balance = balanceRepository.findBalanceByPhoneNumber(request.getPhoneProducer());

        if (balance == null) {
            throw new BalanceException("Balance does not exits.", HttpStatus.BAD_REQUEST);
        }

        try {
            balance = requestToBalanceProducer(balance, request, idUser);
        }catch (Exception e) {
            throw new BalanceException(e.getMessage(), HttpStatus.CONFLICT);
        }

        balanceRepository.save(balance);

        return new MessageResponse("00", "Balance updated successfully.");
    }

    public Balance requestToBalanceProducer(
            Balance balanceProducer,
            UpdateProducerBalanceRequest updateProducerBalanceRequest,
            String idUser) {

        if (balanceProducer.getBalance() == 0 && updateProducerBalanceRequest.getAction() == 2) {
            throw new BalanceException("Balance is not enough.", HttpStatus.BAD_REQUEST);
        }

        balanceProducer.setBalance(newBalance(balanceProducer, updateProducerBalanceRequest));
        balanceProducer.setUpdateDate(LocalDateTime.now());
        balanceProducer.setIdUser(idUser);

        return balanceProducer;
    }

    public long newBalance(Balance balance, UpdateProducerBalanceRequest updateProducerBalanceRequest) {
        long result = 0;

        if (updateProducerBalanceRequest.getAction() == 1) {
            result = addBalanceAmount(balance.getBalance(), updateProducerBalanceRequest.getAmount());
        }

        if (updateProducerBalanceRequest.getAction() == 2) {
            result = subtractBalanceAmount(balance.getBalance(), updateProducerBalanceRequest.getAmount());
        }
        return result;
    }

    public long addBalanceAmount(long currentBalance, long newAmount) {
        return currentBalance + newAmount;
    }

    public long subtractBalanceAmount(long currentBalance, long newAmount) {
        return currentBalance - newAmount;
    }
}
