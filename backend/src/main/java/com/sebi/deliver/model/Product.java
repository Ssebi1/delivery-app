package com.sebi.deliver.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.*;

@NoArgsConstructor(force = true)
@AllArgsConstructor
@ToString
@Data
@Entity @Table(name = "products")
public class Product {
    @Id
    @SequenceGenerator(name = "products_sequence", sequenceName = "products_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "products_sequence")
    @Schema(name = "Product id", description = "Unique identifier", example = "1")
    private Long id;

    @NonNull
    @Schema(name = "Product name", description = "Product name", example = "French fries")
    private String name;

    @Schema(name = "Product description", description = "Product description", example = "This product is amazing!")
    private String description;

    @NonNull
    @PositiveOrZero
    @Schema(name = "Product price", description = "Product price", example = "10.0")
    private Double price;

    @Schema(name = "Product sale price", description = "Product price on sale", example = "5.0")
    private Double salePrice;

    @Schema(name = "Product weight", description = "Product weight", example = "5.0")
    private Double weight;

    @Column( length = 100000)
    @Schema(name = "Product image url", description = "Product image url", example = "https://images.pexels.com/photos/1640777/pexels-photo-1640777.jpeg?auto=compress&cs=tinysrgb&dpr=2&h=650&w=940")
    private String imageUrl = "https://images.pexels.com/photos/1640777/pexels-photo-1640777.jpeg?auto=compress&cs=tinysrgb&dpr=2&h=650&w=940";
}
