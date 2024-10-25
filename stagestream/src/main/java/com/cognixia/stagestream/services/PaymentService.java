package com.cognixia.stagestream.services;

import com.cognixia.stagestream.models.Payment;
import com.cognixia.stagestream.repositories.PaymentRepository;
import com.cognixia.stagestream.exceptions.ResourceNotFoundException;
import com.cognixia.stagestream.config.AppConstants;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.access.prepost.PreAuthorize;
import java.util.List;




@Service
public class PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    @PreAuthorize("hasAnyRole('" + AppConstants.ADMIN_ROLE + "', '" + AppConstants.USER_ROLE + "')")
    public List<Payment> getAllPayments() {
        return paymentRepository.findAll();
    }

    @PreAuthorize("hasAnyRole('" + AppConstants.ADMIN_ROLE + "', '" + AppConstants.USER_ROLE + "')")
    public Payment getPaymentById(Long paymentId) {
        return paymentRepository.findById(paymentId)
                .orElseThrow(() -> new ResourceNotFoundException("Payment with ID " + paymentId + " not found"));
    }

    @PreAuthorize("hasRole('" + AppConstants.ADMIN_ROLE + "')")
    @Transactional
    public Payment createPayment(Payment payment) {
        return paymentRepository.save(payment);
    }

    @PreAuthorize("hasRole('" + AppConstants.ADMIN_ROLE + "')")
    @Transactional
    public Payment updatePayment(Long paymentId, Payment updatedPayment) {
        return paymentRepository.findById(paymentId)
                .map(payment -> {
                    payment.setPaymentMethod(updatedPayment.getPaymentMethod());
                    return paymentRepository.save(payment);
                })
                .orElseThrow(() -> new ResourceNotFoundException("Payment with ID " + paymentId + " not found"));
    }

    @PreAuthorize("hasRole('" + AppConstants.ADMIN_ROLE + "')")
    @Transactional
    public void deletePayment(Long paymentId) {
        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new ResourceNotFoundException("Payment with ID " + paymentId + " not found"));
        paymentRepository.delete(payment);
    }
}