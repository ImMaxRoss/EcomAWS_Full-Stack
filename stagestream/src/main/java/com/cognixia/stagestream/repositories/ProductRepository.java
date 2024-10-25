package com.cognixia.stagestream.repositories;

import com.cognixia.stagestream.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    Optional<Product> findByProductName(String productName);

    void deleteByProductId(Long productId);

    default Product updateProductQuantity(Long productId, Integer newQuantity) {
        Optional<Product> productOpt = findById(productId);
        if (productOpt.isPresent()) {
            Product product = productOpt.get();
            product.setQuantity(newQuantity);
            return save(product);
        }
        return null; 
    }
}