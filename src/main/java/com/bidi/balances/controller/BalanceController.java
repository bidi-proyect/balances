package com.bidi.balances.controller;


import com.bidi.balances.dto.CreateBalanceRequest;
import com.bidi.balances.dto.CreateBalanceResponse;
import com.bidi.balances.dto.UpdateProducerAndReceiverBalanceRequest;
import com.bidi.balances.dto.UpdateProducerBalanceRequest;
import com.bidi.balances.service.CreateBalanceService;
import com.bidi.balances.service.GetAllBalancesService;
import com.bidi.balances.service.GetBalanceByPhoneNumberService;
import com.bidi.balances.service.UpdateProducerAndReceiverService;
import com.bidi.balances.service.UpdateProducerService;
import com.bidi.balances.utils.BalanceException;
import com.bidi.balances.utils.MessageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/balance")
@CrossOrigin("*")
public class BalanceController {

    private final GetAllBalancesService getAllBalancesService;
    private final GetBalanceByPhoneNumberService getBalanceByPhoneNumberService;
    private final CreateBalanceService createBalanceService;
    private final UpdateProducerAndReceiverService updateProducerAndReceiverService;
    private final UpdateProducerService updateProducerService;

    @GetMapping("/")
    public ResponseEntity< List<CreateBalanceResponse> > getAllBalancesService() {
        return new ResponseEntity<>(getAllBalancesService.getAllBalances(), HttpStatus.OK);
    }

    @GetMapping("/{idUser}")
    public ResponseEntity<CreateBalanceResponse> getBalanceByIdUser (@PathVariable String idUser) throws Exception {
        return new ResponseEntity<>(getBalanceByPhoneNumberService.getBalances(idUser), HttpStatus.OK);
    }

    @PostMapping("/")
    public ResponseEntity<MessageResponse> createBalance (@RequestBody CreateBalanceRequest createBalanceRequest) {
        MessageResponse response = createBalanceService.createBalance(createBalanceRequest);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/{idUser}")
    public ResponseEntity<MessageResponse> updateProducerAndReceiverBalance(
            @RequestBody UpdateProducerAndReceiverBalanceRequest updateProducerAndReceiverBalanceRequest,
            @PathVariable String idUser) throws BalanceException {

        MessageResponse response = updateProducerAndReceiverService.updateProducerAndReceiverBalance(
                updateProducerAndReceiverBalanceRequest,
                idUser
        );
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/producer/{idUser}")
    public ResponseEntity<MessageResponse> updateProducerBalance(
            @RequestBody UpdateProducerBalanceRequest updateProducerBalanceRequest,
            @PathVariable String idUser) throws Exception {

        MessageResponse response = updateProducerService.updateProducerBalance(updateProducerBalanceRequest, idUser);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
