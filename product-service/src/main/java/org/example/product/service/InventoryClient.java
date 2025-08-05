package org.example.product.service;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import lombok.RequiredArgsConstructor;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

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


    private HttpHeaders getAuthHeaders() {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest currentRequest = attributes.getRequest();

        String token = currentRequest.getHeader("Authorization");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", token);

        return headers;
    }


    /**
     * Fetch current stock quantity for a product.
     *
     * @param productId ID of the product
     * @return quantity in stock
     */
    public int getStockQuantity(Long productId){

        HttpEntity<Void> entity = new HttpEntity<>(getAuthHeaders());
        ResponseEntity<Integer> response = restTemplate.exchange(
                inventoryServiceUrl + productId,
                HttpMethod.GET,
                entity,
                Integer.class
        );
        return response.getBody() != null ? response.getBody() : 0;

//        Integer quantity = restTemplate.getForObject(
//                inventoryServiceUrl  + productId,
//                Integer.class
//        );
       // return quantity != null ? quantity : 0;
    }

    /**
     * Initialize inventory for a new product.
     *
     * @param productId ID of the product
     * @param quantity  initial stock
     */
    public void createInventory(Long productId, int quantity){
        HttpEntity<InventoryRequest> entity = new HttpEntity<>(new InventoryRequest(productId, quantity), getAuthHeaders());

        restTemplate.exchange(
                inventoryServiceUrl,
                HttpMethod.POST,
                entity,
                Void.class
        );
//        restTemplate.postForObject(
//                inventoryServiceUrl,
//                new InventoryRequest(productId, quantity),
//                Void.class
//                );
    }
}
