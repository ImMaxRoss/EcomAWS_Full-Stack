package com.cognixia.stagestream.services;

import com.cognixia.stagestream.models.Cart;
import com.cognixia.stagestream.repositories.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CartService {

    @Autowired
    private CartRepository cartRepository;

    public List<Cart> getAllCarts() {
        return cartRepository.findAll();
    }

    public Cart getCartById(Long id) {
        Optional<Cart> cart = cartRepository.findById(id);
        return cart.orElseThrow(() -> new RuntimeException("Cart not found"));
    }

    public Cart addCart(Cart cart) {
        return cartRepository.save(cart);
    }

    public Cart updateCart(Long id, Cart cartDetails) {
        Cart cart = getCartById(id);
        cart.setTotalPrice(cartDetails.getTotalPrice());
        cart.setUser(cartDetails.getUser());
        cart.setCartItems(cartDetails.getCartItems());
        return cartRepository.save(cart);
    }

    public void deleteCart(Long id) {
        Cart cart = getCartById(id);
        cartRepository.delete(cart);
    }
}