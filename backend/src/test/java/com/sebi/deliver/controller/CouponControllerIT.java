package com.sebi.deliver.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sebi.deliver.configs.JWTAuthFilter;
import com.sebi.deliver.dto.CouponRequest;
import com.sebi.deliver.model.Coupon;
import com.sebi.deliver.model.security.User;
import com.sebi.deliver.service.CouponService;
import com.sebi.deliver.service.UserService;
import com.sebi.deliver.service.security.JWTUtils;
import com.sebi.deliver.service.security.UserDetailsService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = CouponController.class)
@AutoConfigureMockMvc(addFilters = false)
public class CouponControllerIT {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private CouponService couponService;
    @MockBean
    private UserService userService;
    @MockBean
    private JWTUtils jwtUtils;
    @MockBean
    private UserDetailsService userDetailsService;

    @Test
    @WithMockUser(roles = "ADMIN")
    public void getAllCouponsTest() throws Exception {
        mockMvc.perform(get("/api/coupons/admin"))
                .andExpect(status().isOk());
        verify(couponService, times(1)).getAllCoupons();
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void getUserCouponsTest() throws Exception {
        mockMvc.perform(get("/api/coupons/admin/user/1"))
                .andExpect(status().isOk());
        verify(couponService, times(1)).getUserCoupons(1L);
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void getCouponTest() throws Exception {
        mockMvc.perform(get("/api/coupons/admin/1"))
                .andExpect(status().isOk());
        verify(couponService, times(1)).getCoupon(1L);
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void addCouponTest() throws Exception {
        CouponRequest request = new CouponRequest(10);
        User user = new User();
        user.setId(1L);
        Coupon coupon = new Coupon();
        coupon.setId(1L);
        coupon.setDiscount(10);
        coupon.setUser(user);
        when(userService.getUser(anyLong())).thenReturn(user);
        when(couponService.addCoupon(anyLong(), any())).thenReturn(coupon);
        mockMvc.perform(post("/api/coupons/admin/1")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.discount").value(request.getDiscount()));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void deleteCouponTest() throws Exception {
        mockMvc.perform(get("/api/coupons/admin/1"))
                .andExpect(status().isOk());
        verify(couponService, times(1)).getCoupon(1L);
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void updateCouponTest() throws Exception {
        CouponRequest request = new CouponRequest(10);
        User user = new User();
        user.setId(1L);
        Coupon coupon = new Coupon();
        coupon.setId(1L);
        coupon.setDiscount(10);
        coupon.setUser(user);
        when(userService.getUser(anyLong())).thenReturn(user);
        when(couponService.updateCoupon(anyLong(), any())).thenReturn(coupon);
        mockMvc.perform(put("/api/coupons/admin/1")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.discount").value(request.getDiscount()));
    }
}
