package com.cognixia.stagestream.controllers;

import com.cognixia.stagestream.config.AppConstants;
import com.cognixia.stagestream.models.Product;
import com.cognixia.stagestream.services.ProductService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(AppConstants.PRODUCT_BASE_URL)
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/search")
    public Optional<Product> findByProductName(@RequestParam("name") String productName) {
        return productService.findByProductName(productName);
    }

    @GetMapping
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    @GetMapping("/{id}")
    public Product getProductById(@PathVariable Long id) {
        return productService.getProductById(id);
    }

    @PostMapping
    public Product createProduct(@Valid @RequestBody Product product) {
        return productService.addProduct(product);
    }

    @PutMapping("/{id}")
    public Product updateProduct(@PathVariable Long id, @Valid @RequestBody Product productDetails) {
        return productService.updateProduct(id, productDetails);
    }

    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
    }
}