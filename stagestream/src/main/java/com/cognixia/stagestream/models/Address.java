package com.cognixia.stagestream.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "addresses")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "address_id")
    private Long addressId;

    @Column(name = "street", nullable = false, length = 255)
    private String street;

    @Column(name = "city", nullable = false, length = 255)
    private String city;

    @Column(name = "state", nullable = false, length = 255)
    private String state;

    @Column(name = "zipcode", nullable = false, length = 255)
    private String zipcode;

    // Relationship to Orders
    @ManyToMany(mappedBy = "addresses")
    private List<Order> orders;
}