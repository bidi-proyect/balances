package com.bidi.balances.dto;

import lombok.Data;

@Data
public class CreateBalanceRequest {
    private String idUser;
    private long balance;
    private String phoneNumber;
}
