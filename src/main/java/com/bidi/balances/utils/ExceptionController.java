package com.bidi.balances.utils;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@RequiredArgsConstructor
public class ExceptionController {
    @ExceptionHandler(BalanceException.class)
    public ResponseEntity<MessageResponse> handleBalanceException (BalanceException e) {
        return new ResponseEntity<>(new MessageResponse("01", e.getMessage()), e.getCode());
    }
}
