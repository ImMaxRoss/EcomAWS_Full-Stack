package com.cognixia.stagestream.services;

import com.cognixia.stagestream.config.AppConstants;
import com.cognixia.stagestream.exceptions.ResourceNotFoundException;
import com.cognixia.stagestream.models.Product;
import com.cognixia.stagestream.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public Optional<Product> findByProductName(String productName) {
        return productRepository.findByProductName(productName);
    }

    @PreAuthorize("hasRole('" + AppConstants.USER_ROLE + "') or hasRole('" + AppConstants.ADMIN_ROLE + "')")
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @PreAuthorize("hasRole('" + AppConstants.USER_ROLE + "')")
    public Product getProductById(Long productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + productId));
    }

    @PreAuthorize("hasRole('" + AppConstants.ADMIN_ROLE + "')")
    public Product addProduct(Product product) {
        return productRepository.save(product);
    }

    @PreAuthorize("hasRole('" + AppConstants.ADMIN_ROLE + "')")
    public Product updateProduct(Long productId, Product productDetails) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + productId));
        
        product.setProductName(productDetails.getProductName());
        product.setDescription(productDetails.getDescription());
        product.setQuantity(productDetails.getQuantity());
        product.setPrice(productDetails.getPrice());
        product.setDiscount(productDetails.getDiscount());
        product.setSpecialPrice(productDetails.getSpecialPrice());
        product.setCategory(productDetails.getCategory());

        final Product updatedProduct = productRepository.save(product);
        return updatedProduct;
    }

    @PreAuthorize("hasRole('" + AppConstants.ADMIN_ROLE + "')")
    public void deleteProduct(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + productId));
        
        productRepository.delete(product);
    }
}