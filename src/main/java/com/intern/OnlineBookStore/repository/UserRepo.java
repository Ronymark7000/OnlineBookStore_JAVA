package com.intern.OnlineBookStore.repository;

import com.intern.OnlineBookStore.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<User, Integer>{
    Optional<User> findUserByUsername(String username);

    @Query(value = "SELECT * FROM onlineuser WHERE username = :username AND password = :password LIMIT 1", nativeQuery = true)
    User findUserByUsernameAndPassword(String username, String password);
}
