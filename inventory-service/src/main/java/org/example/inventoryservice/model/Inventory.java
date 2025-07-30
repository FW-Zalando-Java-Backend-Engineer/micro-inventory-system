package org.example.inventoryservice.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Represents the inventory document for a specific product.
 */
@Document(collection = "inventories")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Inventory {
    /** Unique identifier of the inventory record */
    @Id
    private String id;

    /** ID of the product (comes from product-service) */
    private Long productId;

    /** Current quantity in stock */
    private int quantity;
}
