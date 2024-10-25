package com.cognixia.stagestream.controllers;

import com.cognixia.stagestream.models.Cart;
import com.cognixia.stagestream.services.CartService;
import com.cognixia.stagestream.config.AppConstants;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(AppConstants.CART_BASE_URL)
public class CartController {

    @Autowired
    private CartService cartService;

    @GetMapping("")
    public ResponseEntity<List<Cart>> getAllCarts() {
        List<Cart> carts = cartService.getAllCarts();
        return ResponseEntity.ok().body(carts);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cart> getCartById(@PathVariable Long id) {
        Cart cart = cartService.getCartById(id);
        return ResponseEntity.ok().body(cart);
    }

    @PostMapping("")
    public ResponseEntity<Cart> addCart(@RequestBody Cart cart) {
        Cart newCart = cartService.addCart(cart);
        return ResponseEntity.status(201).body(newCart);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Cart> updateCart(@PathVariable Long id, @RequestBody Cart cartDetails) {
        Cart updatedCart = cartService.updateCart(id, cartDetails);
        return ResponseEntity.ok().body(updatedCart);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCart(@PathVariable Long id) {
        cartService.deleteCart(id);
        return ResponseEntity.ok("Cart with ID " + id + " was deleted successfully.");
    }
}