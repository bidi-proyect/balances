package com.bidi.balances.entity;


import lombok.Data;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "BALANCES")
@Data
public class Balance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_balance")
    private long idBalance;
    @Column(name = "id_user")
    private String idUser;
    @Column(name = "balance")
    private long balance;
    @Column(name = "phone_number")
    private String phoneNumber;
    @Column(name = "creation_date")
    private LocalDateTime creationDate;
    @Column(name = "update_date")
    private LocalDateTime updateDate;
}
