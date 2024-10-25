package com.cognixia.stagestream.services;

import com.cognixia.stagestream.config.AppConstants;
import com.cognixia.stagestream.exceptions.ResourceNotFoundException;
import com.cognixia.stagestream.models.OrderItem;
import com.cognixia.stagestream.repositories.OrderItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.access.prepost.PreAuthorize;


import java.util.List;

@Service
public class OrderItemService {

    @Autowired
    private OrderItemRepository orderItemRepository;

    @PreAuthorize("hasRole('" + AppConstants.USER_ROLE + "') or hasRole('" + AppConstants.ADMIN_ROLE + "')")
    public List<OrderItem> getOrderItemsByOrderId(Long orderId) {
        return orderItemRepository.findByOrderId(orderId);
    }

	@PreAuthorize("hasRole('" + AppConstants.ADMIN_ROLE + "')")
	public OrderItem updateOrderItem(Long orderItemId, Integer newQuantity, Double newPrice) {
		return orderItemRepository.findById(orderItemId).map(orderItem -> {
			orderItem.setQuantity(newQuantity);
            orderItem.setOrderedProductPrice(newPrice);
			return orderItemRepository.save(orderItem);
		}).orElseThrow(() -> new ResourceNotFoundException("OrderItem with id " + orderItemId + " not found"));
	}

    @PreAuthorize("hasRole('" + AppConstants.ADMIN_ROLE + "')")
    public OrderItem createOrderItem(OrderItem orderItem) {
        return orderItemRepository.save(orderItem);
    }

    @PreAuthorize("hasRole('" + AppConstants.ADMIN_ROLE + "')")
    public void deleteOrderItem(Long orderItemId) {
        try {
            orderItemRepository.deleteById(orderItemId);
        } catch (Exception e) {
            throw new ResourceNotFoundException("OrderItem with id " + orderItemId + " not found, cannot delete.");
        }
    }

    @PreAuthorize("hasRole('" + AppConstants.ADMIN_ROLE + "')")
    public List<OrderItem> getAllOrderItems() {
        return orderItemRepository.findAll();
    }
}