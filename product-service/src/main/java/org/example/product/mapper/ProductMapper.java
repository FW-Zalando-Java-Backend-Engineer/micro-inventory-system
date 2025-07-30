package org.example.product.mapper;

import org.example.product.dto.ProductDto;
import org.example.product.model.Product;

/**
 * Utility class for converting between Product entity and ProductDto.
 */
public class ProductMapper {

    public static ProductDto  toDTO(Product product, int quantity){
        return new ProductDto(
                product.getId(),
                product.getName(),
                product.getPrice(),
                product.getDescription(),
                quantity
        );
    }
}
