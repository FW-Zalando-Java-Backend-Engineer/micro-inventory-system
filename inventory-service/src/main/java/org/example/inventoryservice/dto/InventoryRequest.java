package org.example.inventoryservice.dto;

/**
 * DTO representing the request to create or update inventory for a product.
 *
 * @param productId the ID of the product
 * @param quantity the quantity to set
 */
public record InventoryRequest(Long productId, int quantity) {
}
