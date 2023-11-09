package com.bidi.balances.repository;


import com.bidi.balances.entity.Balance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BalanceRepository extends JpaRepository<Balance, Long> {

    Balance findBalanceByPhoneNumber(String phoneNumber);
}
