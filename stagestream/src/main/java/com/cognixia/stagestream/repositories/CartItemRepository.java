package com.cognixia.stagestream.repositories;

import com.cognixia.stagestream.models.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    
    @Query("SELECT ci FROM CartItem ci WHERE ci.cart.cartId = :cartId")
    List<CartItem> findByCartId(@Param("cartId") Long cartId);


    default CartItem updateCartItem(CartItem cartItem) {
        return save(cartItem);
    }

    void deleteByCartItemId(Long cartItemId);

    Optional<CartItem> findByCart_CartIdAndProduct_ProductId(Long cartId, Long productId);

}