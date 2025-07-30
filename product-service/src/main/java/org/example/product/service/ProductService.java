package org.example.product.service;

import lombok.RequiredArgsConstructor;
import org.example.product.dto.ProductDto;
import org.example.product.mapper.ProductMapper;
import org.example.product.model.Product;
import org.example.product.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Business logic service for managing products.
 */
@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository repository;
    private final InventoryClient inventoryClient;

    /**
     * Get all products along with their live inventory stock.
     */
    public List<ProductDto> getAllProducts(){
        List<Product> products = repository.findAll();

        List<ProductDto> allProducts = products.stream()
                .map(
                        product -> ProductMapper.toDTO(
                                product,
                                inventoryClient.getStockQuantity(product.getId())
                        )
                )
                .collect(Collectors.toList());

        return allProducts;

    }

    /**
     * Add a new product and initialize inventory for it.
     */
    public ProductDto addProduct(Product product, int quantity){

        Optional<Product> existing = repository.findByName(product.getName());

        if (existing.isPresent()) {
            throw new RuntimeException("Product already exists");
        }

        Product saved = repository.save(product);

        inventoryClient.createInventory(saved.getId(), quantity);

        return ProductMapper.toDTO(saved, quantity);

    }

}
