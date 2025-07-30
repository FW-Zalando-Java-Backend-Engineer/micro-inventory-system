package org.example.inventoryservice.service;

import lombok.RequiredArgsConstructor;
import org.example.inventoryservice.model.Inventory;
import org.example.inventoryservice.repository.InventoryRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Service class that contains the business logic for inventory management.
 */
@Service
@RequiredArgsConstructor
public class InventoryService {
    private final InventoryRepository repository;

    /**
     * Gets the current quantity in stock for the given product.
     *
     * @param productId the product ID
     * @return current stock quantity (0 if not found)
     */
    public int getStock(Long productId){
        Optional<Inventory> inventory = repository.findByProductId(productId);
        return inventory.map(Inventory::getQuantity).orElse(0);

    }

    /**
     * Creates or updates the inventory entry for a product.
     *
     * @param productId the product ID
     * @param quantity  the new stock quantity
     */
    public void createOrUpdateStock(Long productId, int quantity){
        Inventory inventory = repository.findByProductId(productId)
                .orElse(new Inventory());
        inventory.setProductId(productId);
        inventory.setQuantity(quantity);
        repository.save(inventory);
    }
}
