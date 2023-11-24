package com.intern.OnlineBookStore.service;

import com.intern.OnlineBookStore.dto.UserDto;
import com.intern.OnlineBookStore.model.User;
import com.intern.OnlineBookStore.repository.UserRepo;
import com.intern.OnlineBookStore.util.CustomMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    //get all students
    public List<UserDto> getAllUsers() {
            List<User> users = new ArrayList<>(userRepo.findAll());

            List<UserDto> userDtos = CustomMapper.mapUserDtos(users);
            return userDtos;
    }

    public UserDto getUserById(Integer userId)
    {
        Optional<User> optionalUser = userRepo.findById(userId);

        User user = optionalUser.get();
        UserDto userDtos = new UserDto(user.getUserId(),user.getUsername(), user.getEmail(), user.getPassword(),user.getRole());

        return userDtos;
    }


    public User createUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepo.save(user);
    }

    public User updateUser(int id, User updatedUser) {
        Optional<User> optionalUser = userRepo.findById(id);
        if (optionalUser.isPresent()) {
            User existingUser = optionalUser.get();

            // Update properties of the existing user with values from the updated user
            //existingUser.setUserId(updatedUser.getUserId());
            existingUser.setUsername(updatedUser.getUsername());
            existingUser.setEmail(updatedUser.getEmail());
            existingUser.setPassword(passwordEncoder.encode(updatedUser.getPassword()));
            existingUser.setRole(updatedUser.getRole());


            // Save the updated user back to the database
            return userRepo.save(existingUser);

        }
        return updatedUser;
    }



    public User deleteUser(Integer userId) {
        Optional<User> optionalUser = userRepo.findById(userId);
        if (optionalUser.isPresent()) {
            userRepo.deleteById(userId);
        }
            return null;
    }

}
