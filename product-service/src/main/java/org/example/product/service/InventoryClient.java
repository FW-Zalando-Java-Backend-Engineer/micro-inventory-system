package org.example.product.service;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*; // Imports what is needed for working with HTTP requests / responses
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


    /**
     * Builds the HTTP headers required for calling the secured inventory service.
     *   This method extracts the original Authorization header from the
     *    current incoming HTTP request (to the product service), and forwards
     *    it to the inventory service so that the same token can be reused.
     * */
    private HttpHeaders getAuthHeaders() {
        // Step 1: Access the current HTTP request context (bound to the current thread)

        ServletRequestAttributes attributes =
                (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();

        // Step 2: Retrieve the actual HttpServletRequest object (which contains headers)
        HttpServletRequest currentRequest = attributes.getRequest();

        // Step 3: Extract the original Authorization header (Bearer token) from the request
        String token = currentRequest.getHeader("Authorization");

        // Step 4: Create new headers for the outgoing request to inventory-service
        HttpHeaders headers = new HttpHeaders();

        // Step 5: Specify that we're sending JSON in the body (for POST requests)
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Step 6: Set the Authorization header with the token from the original request
        headers.set("Authorization", token);

        // Step 7: Return these headers for use in RestTemplate requests
        return headers;
    }


    /**
     * Fetches current stock quantity for a specific product.
     * This uses a GET request to the inventory service and passes along the JWT token.
     *
     * @param productId ID of the product
     * @return quantity in stock
     */
    public int getStockQuantity(Long productId){
        // Wrap the authorization headers in an HttpEntity (no body needed for GET)
        HttpEntity<Void> entity = new HttpEntity<>(getAuthHeaders());

        // Use restTemplate.exchange to include headers with the GET request
        ResponseEntity<Integer> response = restTemplate.exchange(
                inventoryServiceUrl + productId, // Full URL: e.g. http://localhost:8587/api/inventory/5
                HttpMethod.GET,  // HTTP method type
                entity,  // Entity contains the headers (especially the token)
                Integer.class    // Expected response type (an Integer quantity)
        );
        // Return the body (or 0 if null, just to be safe)
        return response.getBody() != null ? response.getBody() : 0;

    }

    /**
     * Initializes inventory for a new product by sending a POST request to the inventory service.
     * Includes the same Google token from the client in the request header.
     *
     * @param productId ID of the product
     * @param quantity  initial stock
     */
    public void createInventory(Long productId, int quantity){
        // Create a body for the POST request using the InventoryRequest DTO
        InventoryRequest body = new InventoryRequest(productId, quantity);

        // Wrap both body and headers in the HttpEntity
        HttpEntity<InventoryRequest> entity = new HttpEntity<>(body, getAuthHeaders());

        // Use exchange() to POST the request along with the Authorization header
        restTemplate.exchange(
                inventoryServiceUrl, // URL: e.g. http://localhost:8587/api/inventory
                HttpMethod.POST, // HTTP method
                entity,  // Includes JSON body + headers
                Void.class   // No response body expected
        );
    }
}
