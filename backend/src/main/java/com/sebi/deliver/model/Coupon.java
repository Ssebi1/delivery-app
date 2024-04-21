package com.sebi.deliver.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@NoArgsConstructor(force = true)
@AllArgsConstructor
@ToString
@Data
@Entity
@Table(name = "coupons")
public class Coupon {

    @Id
    @SequenceGenerator(name = "coupon_sequence", sequenceName = "coupon_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "coupon_sequence")
    @Schema(name = "Coupon id", description = "Unique identifier", example = "1")
    private Long id;

    @NonNull
    @Schema(name = "Coupon code", description = "Coupon code", example = "123456")
    private String code;

    @NonNull
    @Schema(name = "Coupon discount", description = "Coupon discount percentage", example = "10")
    private Integer discount;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}
