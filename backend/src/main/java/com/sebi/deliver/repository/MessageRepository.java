package com.sebi.deliver.repository;

import com.sebi.deliver.model.Message;
import com.sebi.deliver.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {

    @Query("SELECT m FROM Message m WHERE m.user.id = ?1")
    List<Message> findByUserId(Long id);
}
