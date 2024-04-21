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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private static final Logger logger = LoggerFactory.getLogger(CouponService.class);


    @Autowired
    public CouponService(UserRepository userRepository, CouponRepository couponRepository) {
        this.userRepository = userRepository;
        this.couponRepository = couponRepository;
    }

    public List<Coupon> getAllCoupons() {
        return couponRepository.findAll();
    }

    public List<Coupon> getUserCoupons(Long id) {
        logger.info("Getting coupons for user with id: {}", id);
        User user = userRepository.findById(id).orElseThrow(GenericException::new);
        return couponRepository.findByUserId(id);
    }

    public Coupon addCoupon(Long id, Coupon coupon) {
        logger.info("Adding coupon for user with id: {}", id);
        Optional<User> user = userRepository.findById(id);
        if (user.isEmpty()) {
            logger.error("User with id: {} not found.", id);
            throw new GenericException();
        }
        if (coupon.getDiscount() <= 0) {
            logger.error("Invalid discount.");
            throw new InvalidDiscountException();
        }
        coupon.setUser(user.get());
        coupon.setCode(generateCode());
        couponRepository.save(coupon);
        logger.info("Coupon with code: {} added successfully.", coupon.getCode());
        return coupon;
    }

    public Coupon getCoupon(Long id) {
        logger.info("Getting coupon with id: {}", id);
        Optional<Coupon> coupon = couponRepository.findById(id);
        if (coupon.isEmpty()) {
            logger.error("Coupon with id: {} not found.", id);
            throw new GenericException();
        }
        return coupon.get();
    }

    public Coupon updateCoupon(Long id, Coupon coupon) {
        logger.info("Updating coupon with id: {}", id);
        Optional<Coupon> couponOptional = couponRepository.findById(id);
        if (couponOptional.isEmpty()) {
            logger.error("Coupon with id: {} not found.", id);
            throw new GenericException();
        }
        if (coupon.getDiscount() <= 0) {
            logger.error("Invalid discount.");
            throw new InvalidDiscountException();
        }
        Coupon couponToUpdate = couponOptional.get();
        couponToUpdate.setDiscount(coupon.getDiscount());
        couponRepository.save(couponToUpdate);
        logger.info("Coupon with id: {} updated successfully.", id);
        return couponToUpdate;
    }

    public Coupon deleteCoupon(Long id) {
        logger.info("Deleting coupon with id: {}", id);
        Optional<Coupon> coupon = couponRepository.findById(id);
        if (coupon.isEmpty()) {
            logger.error("Coupon with id: {} not found.", id);
            throw new GenericException();
        }
        couponRepository.delete(coupon.get());
        logger.info("Coupon with id: {} deleted successfully.", id);
        return coupon.get();
    }

    private String generateCode() {
        logger.info("Generating coupon code.");
        byte[] array = new byte[14];
        new Random().nextBytes(array);
        String code = new String(array, StandardCharsets.UTF_8);
        logger.info("Coupon code generated: {}", code);
        return code;
    }
}
