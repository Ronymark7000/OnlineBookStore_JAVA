package com.intern.OnlineBookStore.repository;

import com.intern.OnlineBookStore.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrderRepo extends JpaRepository<Order, Long> {
    @Query(value = "SELECT * FROM orders WHERE user_id = :userId", nativeQuery = true)
    List<Order> getOrdersByUser(long userId);
}