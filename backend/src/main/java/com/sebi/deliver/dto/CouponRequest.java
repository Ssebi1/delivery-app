package com.sebi.deliver.dto;

import io.swagger.annotations.ApiModel;

@ApiModel(value = "Coupon Request", description = "Coupon request")
public class CouponRequest {
    private Integer discount;

    public CouponRequest() {
    }

    public CouponRequest(Integer discount) {
        this.discount = discount;
    }

    public Integer getDiscount() {
        return discount;
    }
}
