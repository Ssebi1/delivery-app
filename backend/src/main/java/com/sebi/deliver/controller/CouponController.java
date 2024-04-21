package com.sebi.deliver.controller;

import com.sebi.deliver.model.ApiError;
import com.sebi.deliver.model.Coupon;
import com.sebi.deliver.service.CouponService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/coupons")
public class CouponController {

    private final CouponService couponService;

    @Autowired
    public CouponController(CouponService couponService) {
        this.couponService = couponService;
    }

    @Operation(summary = "Get all coupons", description = "Get all coupons",
            tags = {"Coupon"},
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successful operation",
                            content = @Content(mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = Coupon.class)))),
                    @ApiResponse(responseCode = "400", description = "Generic exception", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiError.class)))
            })
    @GetMapping
    public List<Coupon> getAllCoupons() {
        return couponService.getAllCoupons();
    }

    @Operation(summary = "Get user coupons", description = "Get user coupons by id",
            tags = {"Coupon"},
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successful operation",
                            content = @Content(mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = Coupon.class)))),
                    @ApiResponse(responseCode = "400", description = "Generic exception", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiError.class))),
                    @ApiResponse(responseCode = "400", description = "User not found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiError.class)))
            })
    @GetMapping("/user/{id}")
    public List<Coupon> getUserCoupons(@PathVariable Long id) {
        return couponService.getUserCoupons(id);
    }


    @Operation(summary = "Get coupon", description = "Get coupon by id",
            tags = {"Coupon"},
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successful operation",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = Coupon.class))),
                    @ApiResponse(responseCode = "400", description = "Generic exception", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiError.class))),
                    @ApiResponse(responseCode = "400", description = "Coupon not found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiError.class)))
            })
    @GetMapping("{id}")
    public Coupon getCoupon(@PathVariable Long id) {
        return couponService.getCoupon(id);
    }

    @Operation(summary = "Add coupon", description = "Add coupon to user by id",
            tags = {"Coupon"},
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successful operation",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = Coupon.class))),
                    @ApiResponse(responseCode = "400", description = "Generic exception", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiError.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid discount", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiError.class))),
                    @ApiResponse(responseCode = "400", description = "User not found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiError.class)))
            })
    @PostMapping("{id}")
    public Coupon addCoupon(@PathVariable Long id, @RequestBody Coupon coupon) {
        return couponService.addCoupon(id, coupon);
    }

    @Operation(summary = "Delete coupon", description = "Delete coupon by id",
            tags = {"Coupon"},
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successful operation",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = Coupon.class))),
                    @ApiResponse(responseCode = "400", description = "Generic exception", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiError.class))),
                    @ApiResponse(responseCode = "400", description = "Coupon not found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiError.class)))
            })
    @DeleteMapping("{id}")
    public void deleteCoupon(@PathVariable Long id) {
        couponService.deleteCoupon(id);
    }

    @Operation(summary = "Update coupon", description = "Update coupon by id",
            tags = {"Coupon"},
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successful operation",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = Coupon.class))),
                    @ApiResponse(responseCode = "400", description = "Generic exception", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiError.class))),
                    @ApiResponse(responseCode = "400", description = "Coupon not found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiError.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid discount", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiError.class)))
            })
    @PutMapping("/{id}")
    public Coupon updateCoupon(@PathVariable Long id, @RequestBody Coupon coupon) {
        return couponService.updateCoupon(id, coupon);
    }
}
