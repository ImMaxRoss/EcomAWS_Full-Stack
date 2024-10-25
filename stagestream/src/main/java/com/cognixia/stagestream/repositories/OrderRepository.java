package com.cognixia.stagestream.repositories;

import com.cognixia.stagestream.models.Order;
import com.cognixia.stagestream.models.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import org.springframework.lang.NonNull;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {


    @NonNull
    List<Order> findAll();


    @NonNull
    Optional<Order> findById(@NonNull Long orderId);

    @Transactional
    @Modifying
    @Query("UPDATE Order o SET o.addresses = ?2 WHERE o.orderId = ?1")
    void addOrderAddress(Long orderId, List<Address> addresses);

    @Query("SELECT o.addresses FROM Order o WHERE o.orderId = ?1")
    List<Address> getAddressByOrderId(Long orderId);

    @Transactional
    @Modifying
    @Query("UPDATE Order o SET o.addresses = (SELECT a FROM o.addresses a WHERE a NOT IN ?2) WHERE o.orderId = ?1")
    void deleteOrderAddress(Long orderId, List<Address> addresses);
}