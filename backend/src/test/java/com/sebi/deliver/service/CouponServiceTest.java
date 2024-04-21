package com.sebi.deliver.service;

import com.sebi.deliver.exception.GenericException;
import com.sebi.deliver.exception.MissingFieldsException;
import com.sebi.deliver.exception.coupon.InvalidDiscountException;
import com.sebi.deliver.model.Coupon;
import com.sebi.deliver.model.Message;
import com.sebi.deliver.model.User;
import com.sebi.deliver.repository.CouponRepository;
import com.sebi.deliver.repository.MessageRepository;
import com.sebi.deliver.repository.UserRepository;
import jdk.jfr.Description;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CouponServiceTest {

    @Mock
    private CouponRepository couponRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private CouponService couponService;

    @Test
    @Description("Test for getting all coupons")
    public void getAllCouponsTest() {
        when(couponRepository.findAll()).thenReturn(List.of(new Coupon(), new Coupon()));
        List<Coupon> coupons = couponService.getAllCoupons();
        assertEquals(2, coupons.size());
    }

    @Test
    @Description("Test for getting all coupons for a user")
    public void getUserCouponsTest() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(new User()));
        when(couponRepository.findByUserId(1L)).thenReturn(List.of(new Coupon(), new Coupon()));
        List<Coupon> coupons = couponService.getUserCoupons(1L);
        assertEquals(2, coupons.size());
    }

    @Test
    @Description("Test for adding a coupon")
    public void addCouponTest() {
        User user = new User();
        user.setId(1L);
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        Coupon coupon = new Coupon();
        coupon.setUser(user);
        coupon.setDiscount(10);
        when(couponRepository.save(any())).thenReturn(coupon);
        Coupon createdCoupon = couponService.addCoupon(1L, coupon);
        assertEquals(1L, coupon.getUser().getId());
        assertEquals(10, coupon.getDiscount());
    }

    @Test
    @Description("Test for getting a coupon")
    public void getCouponTest() {
        Coupon coupon = new Coupon();
        coupon.setId(1L);
        when(couponRepository.findById(1L)).thenReturn(Optional.of(coupon));
        Coupon foundCoupon = couponService.getCoupon(1L);
        assertEquals(1L, foundCoupon.getId());
    }

    @Test
    @Description("Test for updating a coupon")
    public void updateCouponTest() {
        Coupon coupon = new Coupon();
        coupon.setId(1L);
        coupon.setDiscount(10);
        when(couponRepository.findById(1L)).thenReturn(Optional.of(coupon));
        Coupon updatedCoupon = couponService.updateCoupon(1L, coupon);
        assertEquals(1L, updatedCoupon.getId());
        assertEquals(10, updatedCoupon.getDiscount());
    }

    @Test
    @Description("Test for deleting a coupon")
    public void deleteCouponTest() {
        Coupon coupon = new Coupon();
        coupon.setId(1L);
        when(couponRepository.findById(1L)).thenReturn(Optional.of(coupon));
        Coupon deletedCoupon = couponService.deleteCoupon(1L);
        assertEquals(1L, deletedCoupon.getId());
    }

    @ParameterizedTest
    @ValueSource(ints = {0, -1, -10})
    @Description("Test for adding a coupon with invalid discount")
    public void addCouponInvalidDiscountTest(int discount) {
        User user = new User();
        user.setId(1L);
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        Coupon coupon = new Coupon();
        coupon.setUser(user);
        coupon.setDiscount(discount);
        assertThrows(InvalidDiscountException.class, () -> couponService.addCoupon(1L, coupon));
    }

    @Test
    @Description("Test for getting a coupon that does not exist")
    public void getCouponNotFoundTest() {
        when(couponRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(GenericException.class, () -> couponService.getCoupon(1L));
    }

    @Test
    @Description("Test for updating a coupon that does not exist")
    public void updateCouponNotFoundTest() {
        when(couponRepository.findById(1L)).thenReturn(Optional.empty());
        Coupon coupon = new Coupon();
        coupon.setId(1L);
        coupon.setDiscount(10);
        assertThrows(GenericException.class, () -> couponService.updateCoupon(1L, coupon));
    }

    @Test
    @Description("Test for deleting a coupon that does not exist")
    public void deleteCouponNotFoundTest() {
        when(couponRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(GenericException.class, () -> couponService.deleteCoupon(1L));
    }

    @Test
    @Description("Test for getting all coupons for a user that does not exist")
    public void getUserCouponsNotFoundTest() {
        assertThrows(GenericException.class, () -> couponService.getUserCoupons(1L));
    }

    @Test
    @Description("Test for adding a coupon for a user that does not exist")
    public void addCouponUserNotFoundTest() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());
        Coupon coupon = new Coupon();
        coupon.setDiscount(10);
        assertThrows(GenericException.class, () -> couponService.addCoupon(1L, coupon));
    }

    @Test
    @Description("Test for updating a coupon with invalid discount")
    public void updateCouponInvalidDiscountTest() {
        Coupon coupon = new Coupon();
        coupon.setId(1L);
        coupon.setDiscount(-10);
        when(couponRepository.findById(1L)).thenReturn(Optional.of(coupon));
        assertThrows(InvalidDiscountException.class, () -> couponService.updateCoupon(1L, coupon));
    }
}
