package com.intern.OnlineBookStore.repository;

import com.intern.OnlineBookStore.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CartRepo extends JpaRepository<Cart, Long> {
    @Query(value = "SELECT * FROM cart WHERE user_id = :userId", nativeQuery = true)
    List<Cart> findAllBooksByUser(long userId);

    @Query(value = "SELECT * FROM cart WHERE user_id = :userId AND book_id = :bookId LIMIT 1", nativeQuery = true)
    Cart findSingleCartByUser(long userId, long bookId);
}
