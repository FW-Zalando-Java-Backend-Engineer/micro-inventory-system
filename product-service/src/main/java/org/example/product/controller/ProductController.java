package org.example.product.controller;

import lombok.RequiredArgsConstructor;
import org.example.product.dto.ProductDto;
import org.example.product.model.Product;
import org.example.product.service.ProductService;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * REST controller exposing product-related HTTP endpoints.
 */
@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService service;

    /**
     * Internal request wrapper for product + quantity in POST body.
     */
    public record ProductWithQuantity(Product product, int quantity) {}

    /**
     * GET /products — fetch all products with live stock quantity.
     */
    @GetMapping
    public List<ProductDto> getAllProducts() {
        return service.getAllProducts();
    }

    /**
     * POST /products — add a product and initialize its inventory.
     */
    @PostMapping
    public ProductDto addProduct(@RequestBody ProductWithQuantity request) {
        return service.addProduct(request.product(), request.quantity());
    }
}
