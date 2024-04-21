package com.sebi.deliver.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import jakarta.validation.constraints.NotBlank;

@ApiModel(value = "ProductRequest", description = "Product request")
public class ProductRequest {
    @NotBlank(message = "Name is mandatory")
    @ApiModelProperty(value = "Product name", required = true)
    private String name;

    @ApiModelProperty(value = "Product description")
    private String description;

    @NotBlank(message = "Price is mandatory")
    @ApiModelProperty(value = "Product price", required = true)
    private Double price;

    @ApiModelProperty(value = "Product sale price")
    private Double salePrice;

    @ApiModelProperty(value = "Product weight")
    private Double weight;

    @ApiModelProperty(value = "Product image url")
    private String imageUrl;

    public ProductRequest() {
    }

    public ProductRequest(String name, String description, Double price, Double salePrice, Double weight, String imageUrl) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.salePrice = salePrice;
        this.weight = weight;
        this.imageUrl = imageUrl;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Double getPrice() {
        return price;
    }

    public Double getSalePrice() {
        return salePrice;
    }

    public Double getWeight() {
        return weight;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}
