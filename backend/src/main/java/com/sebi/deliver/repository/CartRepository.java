package com.sebi.deliver.repository;

import com.sebi.deliver.model.CartItem;
import com.sebi.deliver.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<CartItem, Long> {

    @Query("SELECT c FROM CartItem c WHERE c.user.id = ?1")
    List<CartItem> findByUserId(Long id);

    @Query("SELECT c FROM CartItem c WHERE c.user.id = ?1 and c.product.id = ?2")
    Optional<CartItem> findByUserIdAndProductId(Long id, long productId);
}
