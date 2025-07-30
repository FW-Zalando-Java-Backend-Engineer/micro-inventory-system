package org.example.product.model;


import jakarta.persistence.*;
import lombok.*;

/**
 * Entity representing a product in the catalog.
 */
@Entity
@Table(name = "products")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private double price;
    private String description;
}
