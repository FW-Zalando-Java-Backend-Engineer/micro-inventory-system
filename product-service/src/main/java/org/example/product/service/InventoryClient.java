package org.example.product.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import lombok.RequiredArgsConstructor;

/**
 * HTTP client to communicate with the inventory-service.
 */
@Component
@RequiredArgsConstructor
public class InventoryClient {

    private final RestTemplate restTemplate;

    @Value("${INVENTORY_SERVICE_URL}")
    private String inventoryServiceUrl;

    /**
     * Internal DTO for POST request body.
     */
    public record InventoryRequest(Long productId, int quantity) {}


    /**
     * Fetch current stock quantity for a product.
     *
     * @param productId ID of the product
     * @return quantity in stock
     */
    public int getStockQuantity(Long productId){
        Integer quantity = restTemplate.getForObject(
                inventoryServiceUrl + "/" + productId,
                Integer.class
        );
        return quantity != null ? quantity : 0;
    }

    /**
     * Initialize inventory for a new product.
     *
     * @param productId ID of the product
     * @param quantity  initial stock
     */
    public void createInventory(Long productId, int quantity){
        restTemplate.postForObject(
                inventoryServiceUrl,
                new InventoryRequest(productId, quantity),
                Void.class
                );
    }
}
