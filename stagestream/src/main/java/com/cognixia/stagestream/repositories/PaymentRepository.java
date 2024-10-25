package com.cognixia.stagestream.repositories;

import com.cognixia.stagestream.models.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.lang.NonNull;

import java.util.Optional;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {

    @NonNull
    Optional<Payment> findById(@NonNull Long paymentId);

}