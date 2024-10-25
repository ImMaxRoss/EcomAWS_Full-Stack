package com.cognixia.stagestream.services;

import com.cognixia.stagestream.models.Cart;
import com.cognixia.stagestream.models.CartItem;
import com.cognixia.stagestream.models.Product;
import com.cognixia.stagestream.repositories.CartItemRepository;
import com.cognixia.stagestream.repositories.CartRepository;
import com.cognixia.stagestream.repositories.ProductRepository;
import com.cognixia.stagestream.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class CartItemService {

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private CartRepository cartRepository;
    
    @Autowired
    private ProductRepository productRepository;

    public CartItem addOrUpdateCartItem(Long userId, Long productId, int quantity) {
        Cart cart = cartRepository.findByUser_UserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Cart not found for user ID: " + userId));

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with ID: " + productId));

        Optional<CartItem> existingCartItemOpt = cartItemRepository.findByCart_CartIdAndProduct_ProductId(cart.getCartId(), productId);

        CartItem cartItem;
        if (existingCartItemOpt.isPresent()) {
            cartItem = existingCartItemOpt.get();
            cartItem.setQuantity(cartItem.getQuantity() + quantity);
        } else {
            cartItem = new CartItem();
            cartItem.setCart(cart);
            cartItem.setProduct(product);
            cartItem.setQuantity(quantity);
            cartItem.setProductPrice(product.getPrice());
            cartItem.setDiscount(product.getDiscount());
        }

        return cartItemRepository.save(cartItem);
    }


    public List<CartItem> getCartItemsByCartId(Long cartId) {
        return cartItemRepository.findByCartId(cartId);
    }


    public CartItem getCartItemById(Long cartItemId) {
        return cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new ResourceNotFoundException("Cart item not found with ID: " + cartItemId));
    }

    public CartItem addCartItem(CartItem cartItem) {
        Product product = cartItem.getProduct();
        
        cartItem.setProductName(product.getProductName());
        cartItem.setProductDescription(product.getDescription());
        cartItem.setProductCategory(product.getCategory());
        cartItem.setProductPrice(product.getPrice());
        cartItem.setDiscount(product.getDiscount());

        return cartItemRepository.save(cartItem);
    }


    public CartItem updateCartItem(CartItem cartItem) {
        CartItem existingCartItem = cartItemRepository.findById(cartItem.getCartItemId())
                .orElseThrow(() -> new ResourceNotFoundException("Cart item not found with ID: " + cartItem.getCartItemId()));

        existingCartItem.setQuantity(cartItem.getQuantity());
        existingCartItem.setDiscount(cartItem.getDiscount());
        existingCartItem.setProductPrice(cartItem.getProductPrice());
        existingCartItem.setProduct(cartItem.getProduct());
        existingCartItem.setCart(cartItem.getCart());

        return cartItemRepository.save(existingCartItem);
    }


    @Transactional
    public void deleteCartItem(Long cartItemId) {
        if (!cartItemRepository.existsById(cartItemId)) {
            throw new ResourceNotFoundException("Cart item not found with ID: " + cartItemId);
        }
        cartItemRepository.deleteByCartItemId(cartItemId);
    }


}