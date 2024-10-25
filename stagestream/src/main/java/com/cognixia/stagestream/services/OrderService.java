package com.cognixia.stagestream.services;

import com.cognixia.stagestream.models.Order;
import com.cognixia.stagestream.models.Address;
import com.cognixia.stagestream.repositories.OrderRepository;
import com.cognixia.stagestream.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public Order getOrderById(Long orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order with ID " + orderId + " not found"));
    }

    @PreAuthorize("hasAuthority('" + com.cognixia.stagestream.config.AppConstants.ADMIN_ROLE + "')")
    public Order saveOrder(Order order) {
        return orderRepository.save(order);
    }

    @PreAuthorize("hasAuthority('" + com.cognixia.stagestream.config.AppConstants.ADMIN_ROLE + "')")
    public Order updateOrder(Long orderId, Order updatedOrder) {
        return orderRepository.findById(orderId).map(order -> {
            order.setEmail(updatedOrder.getEmail());
            order.setOrderDate(updatedOrder.getOrderDate());
            order.setPayment(updatedOrder.getPayment());
            order.setTotalAmount(updatedOrder.getTotalAmount());
            order.setOrderStatus(updatedOrder.getOrderStatus());
            order.setOrderItems(updatedOrder.getOrderItems());
            order.setAddresses(updatedOrder.getAddresses());
            return orderRepository.save(order);
        }).orElseThrow(() -> new ResourceNotFoundException("Order with ID " + orderId + " not found"));
    }

    @PreAuthorize("hasAuthority('" + com.cognixia.stagestream.config.AppConstants.ADMIN_ROLE + "')")
    public void deleteOrder(Long orderId) {
        if (!orderRepository.existsById(orderId)) {
            throw new ResourceNotFoundException("Order with ID " + orderId + " not found");
        }
        orderRepository.deleteById(orderId);
    }

    @PreAuthorize("hasAuthority('" + com.cognixia.stagestream.config.AppConstants.ADMIN_ROLE + "')")
    @Transactional
    public void addOrderAddress(Long orderId, List<Address> addresses) {
        orderRepository.addOrderAddress(orderId, addresses);
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public List<Address> getAddressesByOrderId(Long orderId) {
        return orderRepository.getAddressByOrderId(orderId);
    }

    @PreAuthorize("hasAuthority('" + com.cognixia.stagestream.config.AppConstants.ADMIN_ROLE + "')")
    @Transactional
    public void deleteOrderAddress(Long orderId, List<Address> addresses) {
        orderRepository.deleteOrderAddress(orderId, addresses);
    }
}