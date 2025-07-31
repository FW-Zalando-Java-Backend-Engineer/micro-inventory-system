package org.example.inventoryservice.controller;

import lombok.RequiredArgsConstructor;
import org.example.inventoryservice.dto.InventoryRequest;
import org.example.inventoryservice.service.InventoryService;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller for managing inventory.
 */
@RestController
@RequestMapping("/api/inventory")
@RequiredArgsConstructor
public class InventoryController {

    private final InventoryService inventoryService;

    /**
     * GET /{productId} — returns the current stock for a product.
     */
    @GetMapping("/{productId}")
    public int getStock(@PathVariable Long productId) {
        return inventoryService.getStock(productId);
    }

    /**
     * POST / — creates or updates the stock entry for a product.
     */
    @PostMapping({ "", "/" })
    public void createOrUpdate(@RequestBody InventoryRequest request) {
        inventoryService.createOrUpdateStock(request.productId(), request.quantity());
    }
}
