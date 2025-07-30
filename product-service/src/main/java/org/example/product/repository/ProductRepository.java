package org.example.product.repository;


import org.example.product.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
/**
 * Repository interface for Product entity, providing CRUD operations.
 */
public interface ProductRepository extends JpaRepository<Product, Long> {

    /**
     * Find a product by its name.
     *
     * @param name product name
     * @return Optional containing the product if found
     */
    Optional<Product> findByName(String name);
}
