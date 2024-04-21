package com.sebi.deliver.repository;

import com.sebi.deliver.model.Product;
import com.sebi.deliver.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query("SELECT p FROM Product p WHERE p.name like ?1")
    Optional<Product> findByName(String email);

}
