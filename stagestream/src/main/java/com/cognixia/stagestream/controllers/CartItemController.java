package com.cognixia.stagestream.controllers;

import com.cognixia.stagestream.config.AppConstants;
import com.cognixia.stagestream.models.AddToCartRequest;
import com.cognixia.stagestream.models.CartItem;
import com.cognixia.stagestream.models.User;
import com.cognixia.stagestream.services.CartItemService;
import com.cognixia.stagestream.services.UserService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(AppConstants.CART_ITEMS_URL)
public class CartItemController {

    @Autowired
    private CartItemService cartItemService;

    @Autowired
    private UserService userService;

    @GetMapping("/cart/{cartId}")
    public ResponseEntity<List<CartItem>> getCartItemsByCartId(@PathVariable Long cartId) {
        return new ResponseEntity<>(cartItemService.getCartItemsByCartId(cartId), HttpStatus.OK);
    }

    @GetMapping("/{cartItemId}")
    public ResponseEntity<CartItem> getCartItemById(@PathVariable Long cartItemId) {
        CartItem cartItem = cartItemService.getCartItemById(cartItemId);
        return new ResponseEntity<>(cartItem, HttpStatus.OK);
    }
    @PostMapping
    public ResponseEntity<CartItem> addCartItem(@RequestBody CartItem cartItem) {
        return new ResponseEntity<>(cartItemService.addCartItem(cartItem), HttpStatus.CREATED);
    }

    @PutMapping("/{cartItemId}")
    public ResponseEntity<CartItem> updateCartItem(@PathVariable Long cartItemId, @RequestBody CartItem cartItem) {
        cartItem.setCartItemId(cartItemId); // Ensure the ID is set correctly
        return new ResponseEntity<>(cartItemService.updateCartItem(cartItem), HttpStatus.OK);
    }

    // DELETE - Remove a cart item
    @DeleteMapping("/{cartItemId}")
    public ResponseEntity<String> deleteCartItem(@PathVariable Long cartItemId) {
        cartItemService.deleteCartItem(cartItemId);
        return new ResponseEntity<>("Cart item deleted successfully", HttpStatus.OK);
    }
    
    @PostMapping("/add")
    public ResponseEntity<CartItem> addOrUpdateCartItem(@RequestBody AddToCartRequest addToCartRequest) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.getUserByUsername(username);
        Long userId = user.getUserId();

        Long productId = addToCartRequest.getProductId();
        int quantity = addToCartRequest.getQuantity();

        CartItem cartItem = cartItemService.addOrUpdateCartItem(userId, productId, quantity);
        return new ResponseEntity<>(cartItem, HttpStatus.OK);
    }

    // Implement this method to retrieve the current user's ID
    public Long getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userService.getUserByUsername(username);
        return user.getUserId();
    }
}