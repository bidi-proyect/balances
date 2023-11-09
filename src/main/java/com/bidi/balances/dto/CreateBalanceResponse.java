package com.bidi.balances.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CreateBalanceResponse {
    private long idBalance;
    private String idUser;
    private long balance;
    private String phoneNumber;
    private LocalDateTime creationDate;
    private LocalDateTime updateDate;
}
