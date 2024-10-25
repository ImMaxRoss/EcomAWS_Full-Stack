package com.cognixia.stagestream.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "payments")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "payment_id")
    private Long paymentId;

    @Column(name = "payment_method", nullable = false, length = 255)
    private String paymentMethod;

    @OneToMany(mappedBy = "payment", cascade = CascadeType.ALL)
    private List<Order> orders;
}
