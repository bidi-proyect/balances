package com.bidi.balances.service.impl;

import com.bidi.balances.dto.UpdateProducerAndReceiverBalanceRequest;
import com.bidi.balances.entity.Balance;
import com.bidi.balances.repository.BalanceRepository;
import com.bidi.balances.service.UpdateProducerAndReceiverService;
import com.bidi.balances.utils.BalanceException;
import com.bidi.balances.utils.MessageResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.Objects;


@Service
@RequiredArgsConstructor
public class UpdateProducerAndReceiverImpl implements UpdateProducerAndReceiverService {

    private final BalanceRepository balanceRepository;
    private static final Logger logger = LoggerFactory.getLogger(UpdateProducerAndReceiverImpl.class);

    @Override
    public MessageResponse updateProducerAndReceiverBalance(
            UpdateProducerAndReceiverBalanceRequest updateProducerAndReceiverBalanceRequest,
            String idUser) throws BalanceException {
        logger.info("Request is maid...");

        Balance balanceProducer = balanceRepository.findBalanceByPhoneNumber(updateProducerAndReceiverBalanceRequest.getPhoneProducer());
        Balance balanceReceiver = balanceRepository.findBalanceByPhoneNumber(updateProducerAndReceiverBalanceRequest.getPhoneReceiver());

        try {
            balanceValidations(balanceProducer, balanceReceiver);
            balanceProducer = requestToBalanceProducer(balanceProducer, updateProducerAndReceiverBalanceRequest, idUser);
            logger.info("Ok....");
        }catch (Exception e) {
            logger.error("Error: {}",e.getMessage());
            throw new BalanceException("Error: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }

        balanceReceiver = requestToBalanceReceiver(balanceReceiver, updateProducerAndReceiverBalanceRequest);
        balanceRepository.save(balanceProducer);
        balanceRepository.save(balanceReceiver);
        logger.info("Balances updated...");
        logger.info("Request finished.");
        return new MessageResponse("00", "Balance updated successfully.");
    }

    public void balanceValidations(Balance balanceProducer, Balance balanceReceiver) throws BalanceException {
        if (balanceProducer == null && balanceReceiver == null) {
            logger.error("Balances not exists.");
            throw new BalanceException("Balances not exists.", HttpStatus.BAD_REQUEST);
        }else if (balanceProducer == null) {
            logger.error("BalanceProducer not exists.");
            throw new BalanceException("BalanceProducer not exist.", HttpStatus.BAD_REQUEST);
        }else if (balanceReceiver == null) {
            logger.error("BalanceReceiver not exists.");
            throw new BalanceException("BalanceReceiver not exist.", HttpStatus.BAD_REQUEST);
        }else if (Objects.equals(balanceProducer.getPhoneNumber(), balanceReceiver.getPhoneNumber())) {
            logger.error("Phone number cant be the same.");
            throw new BalanceException("Phone number cant be the same.", HttpStatus.BAD_REQUEST);
        }
    }

    public Balance requestToBalanceProducer(
            Balance balanceProducer,
            UpdateProducerAndReceiverBalanceRequest updateProducerAndReceiverBalanceRequest,
            String idUser) throws Exception {

        if (balanceProducer.getBalance() < updateProducerAndReceiverBalanceRequest.getAmount()) {
            logger.error("Balance is not enough.");
            throw new Exception("Balance is not enough.");
        }

        balanceProducer.setBalance(subtractBalanceAmount(balanceProducer.getBalance(), updateProducerAndReceiverBalanceRequest.getAmount()));
        balanceProducer.setUpdateDate(LocalDateTime.now());
        balanceProducer.setIdUser(idUser);
        return balanceProducer;
    }

    public Balance requestToBalanceReceiver(
            Balance balanceReceiver,
            UpdateProducerAndReceiverBalanceRequest updateProducerAndReceiverBalanceRequest) {
        balanceReceiver.setBalance(addBalanceAmount(balanceReceiver.getBalance(), updateProducerAndReceiverBalanceRequest.getAmount()));
        balanceReceiver.setUpdateDate(LocalDateTime.now());
        return balanceReceiver;
    }

    public long addBalanceAmount(long currentBalance, long newAmount) {
        return currentBalance + newAmount;
    }

    public long subtractBalanceAmount(long currentBalance, long newAmount) {
        return currentBalance - newAmount;
    }
}
