package com.cognixia.stagestream.repositories;

import com.cognixia.stagestream.models.Cart;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {

    Optional<Cart> findByUser_UserId(Long userId);
}