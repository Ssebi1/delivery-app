package com.sebi.deliver.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import jakarta.validation.constraints.NotBlank;

@ApiModel(value = "Order Request", description = "Order request")
public class OrderRequest {
    private Long user_id;
    @NotBlank(message = "Products are mandatory")
    @ApiModelProperty(value = "Order products", required = true)
    private String products;
    @NotBlank(message = "Price is mandatory")
    @ApiModelProperty(value = "Order price", required = true)
    private Double price;
    private String date;

    public OrderRequest() {
    }

    public OrderRequest(Long user_id, String products, Double price, String date) {
        this.user_id = user_id;
        this.products = products;
        this.price = price;
        this.date = date;
    }

    public Long getUser_id() {
        return user_id;
    }

    public String getProducts() {
        return products;
    }

    public Double getPrice() {
        return price;
    }

    public String getDate() {
        return date;
    }
}
