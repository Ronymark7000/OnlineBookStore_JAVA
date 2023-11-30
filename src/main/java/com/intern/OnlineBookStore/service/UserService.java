package com.intern.OnlineBookStore.service;

import com.intern.OnlineBookStore.dto.UserDto;
import com.intern.OnlineBookStore.model.User;

import java.util.List;

public interface UserService {
    List<UserDto> getAllUsers();
    UserDto getUserById(int userId);
    User createUser(User user);
    User updateUser(int id, User updatedUser);
    User deleteUser(int userId);
    User logIn(String username, String password);

}
