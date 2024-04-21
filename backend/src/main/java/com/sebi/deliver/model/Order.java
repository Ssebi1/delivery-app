package com.sebi.deliver.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.Date;

@NoArgsConstructor(force = true)
@AllArgsConstructor
@ToString
@Data
@Entity @Table(name = "user_order")
public class Order {
    @Id
    @SequenceGenerator(name = "order_sequence", sequenceName = "order_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "order_sequence")
    @Schema(name = "Order id", description = "Unique identifier", example = "1")
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @NotNull
    @Column( length = 100000)
    private String products;

    @NonNull
    @Schema(name = "Order price", description = "Order price", example = "10.0")
    private Double price;

    @Schema(name = "Order date", description = "Order date", example = "2021-01-01 10:22:00")
    private String date;
}
