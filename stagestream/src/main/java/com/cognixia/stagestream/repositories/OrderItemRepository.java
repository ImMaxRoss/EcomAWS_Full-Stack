package com.cognixia.stagestream.repositories;

import com.cognixia.stagestream.models.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.lang.NonNull;

import java.util.List;


@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
    
    @Query("SELECT oi FROM OrderItem oi WHERE oi.order.orderId = :orderId")
    List<OrderItem> findByOrderId(@Param("orderId") Long orderId);
    
    default OrderItem updateOrderItem(Long orderItemId, Integer newQuantity, Double newPrice) {
        return findById(orderItemId).map(orderItem -> {
            orderItem.setQuantity(newQuantity);
            orderItem.setOrderedProductPrice(newPrice);
            return save(orderItem);
        }).orElse(null); 
    }
    
    void deleteById(@NonNull Long orderItemId);
}