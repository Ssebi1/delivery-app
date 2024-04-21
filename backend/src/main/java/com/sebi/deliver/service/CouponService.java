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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class CouponService {

    private final UserRepository userRepository;
    private final CouponRepository couponRepository;


    @Autowired
    public CouponService(UserRepository userRepository, CouponRepository couponRepository) {
        this.userRepository = userRepository;
        this.couponRepository = couponRepository;
    }

    public List<Coupon> getAllCoupons() {
        return couponRepository.findAll();
    }

    public List<Coupon> getUserCoupons(Long id) {
        User user = userRepository.findById(id).orElseThrow(GenericException::new);
        return couponRepository.findByUserId(id);
    }

    public Coupon addCoupon(Long id, Coupon coupon) {
        Optional<User> user = userRepository.findById(id);
        if (user.isEmpty()) {
            throw new GenericException();
        }
        if (coupon.getDiscount() <= 0) {
            throw new InvalidDiscountException();
        }
        coupon.setUser(user.get());
        coupon.setCode(generateCode());
        couponRepository.save(coupon);
        return coupon;
    }

    public Coupon getCoupon(Long id) {
        Optional<Coupon> coupon = couponRepository.findById(id);
        if (coupon.isEmpty()) {
            throw new GenericException();
        }
        return coupon.get();
    }

    public Coupon updateCoupon(Long id, Coupon coupon) {
        Optional<Coupon> couponOptional = couponRepository.findById(id);
        if (couponOptional.isEmpty()) {
            throw new GenericException();
        }
        if (coupon.getDiscount() <= 0) {
            throw new InvalidDiscountException();
        }
        Coupon couponToUpdate = couponOptional.get();
        couponToUpdate.setDiscount(coupon.getDiscount());
        couponRepository.save(couponToUpdate);
        return couponToUpdate;
    }

    public Coupon deleteCoupon(Long id) {
        Optional<Coupon> coupon = couponRepository.findById(id);
        if (coupon.isEmpty()) {
            throw new GenericException();
        }
        couponRepository.delete(coupon.get());
        return coupon.get();
    }

    private String generateCode() {
        byte[] array = new byte[14];
        new Random().nextBytes(array);
        return new String(array, StandardCharsets.UTF_8);
    }
}
