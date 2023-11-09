package com.bidi.balances.service.impl;

import com.bidi.balances.dto.UpdateProducerBalanceRequest;
import com.bidi.balances.entity.Balance;
import com.bidi.balances.repository.BalanceRepository;
import com.bidi.balances.service.UpdateProducerService;
import com.bidi.balances.utils.MessageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class UpdateProducerBalanceImpl implements UpdateProducerService {
    private final BalanceRepository balanceRepository;

    @Override
    public MessageResponse updateProducerBalance(UpdateProducerBalanceRequest updateProducerBalanceRequest, String idUser) {
        Balance balance = balanceRepository.findBalanceByPhoneNumber(updateProducerBalanceRequest.getPhoneProducer());

        if (balance == null) {
            return new MessageResponse("Balance not exist.");
        }

        try {
            balance = requestToBalanceProducer(balance, updateProducerBalanceRequest, idUser);
        }catch (Exception e) {
            return new MessageResponse("Error: " + e.getMessage());
        }

        balanceRepository.save(balance);
        return new MessageResponse("Balance updated successfully.");
    }

    public Balance requestToBalanceProducer(
            Balance balanceProducer,
            UpdateProducerBalanceRequest updateProducerBalanceRequest,
            String idUser) throws Exception {

        if (balanceProducer.getBalance() == 0 && updateProducerBalanceRequest.getAction() == 2) {
            throw new Exception("Balance is not enough.");
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
