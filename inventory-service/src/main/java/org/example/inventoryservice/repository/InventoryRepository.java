package org.example.inventoryservice.repository;

import org.example.inventoryservice.model.Inventory;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

/**
 * MongoDB repository for inventory records.
 */
public interface InventoryRepository extends MongoRepository<Inventory, String> {

    /**
     * Finds inventory by product ID.
     *
     * @param productId the ID of the product
     * @return an optional inventory record
     */
    Optional<Inventory> findByProductId(Long productId);
}
