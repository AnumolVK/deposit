package com.svadhan.deposit.repository;

import com.svadhan.deposit.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DepositRepository extends JpaRepository<Payment, Long> {
    // You can define custom query methods if needed
}
