package com.intern.OnlineBookStore.controller;

import com.intern.OnlineBookStore.dto.UserDto;
import com.intern.OnlineBookStore.service.UserService;
import com.intern.OnlineBookStore.util.ResponseWrapper;
import com.intern.OnlineBookStore.model.User;
import com.intern.OnlineBookStore.service.UserServiceImpl;
import com.intern.OnlineBookStore.util.CustomMapper;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    private ResponseEntity<ResponseWrapper> getAllUsers() {
        List<UserDto> user = userService.getAllUsers();
        ResponseWrapper response = new ResponseWrapper();
        response.setStatusCode(HttpStatus.OK.value());
        response.setMessage("Users retrieved successfully");
        response.setResponse(user);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/user/{userId}")
    private ResponseEntity<ResponseWrapper> getUserById(@PathVariable ("userId") int userId) {

        UserDto user = userService.getUserById(userId);
        if (user != null) {
            ResponseWrapper response = new ResponseWrapper();
            response.setStatusCode(HttpStatus.OK.value());
            response.setMessage("Users retrieved successfully by ID");
            response.setResponse(userService.getUserById(userId));
            return ResponseEntity.ok(response);
        } else {
            ResponseWrapper response = new ResponseWrapper();
            response.setStatusCode(HttpStatus.NOT_FOUND.value());
            response.setMessage("User not found...Please Check Again");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    @PostMapping("/users")
    private ResponseEntity<ResponseWrapper> createUser(@Valid @RequestBody User user){
//      userService.createUser(user);
        ResponseWrapper response = new ResponseWrapper();
        response.setStatusCode(HttpStatus.CREATED.value());
        response.setMessage("Successfully Registered where User Id: " + user.getUserId() );
        response.setResponse(userService.createUser(user));
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/user/{userId}")
    private ResponseEntity<ResponseWrapper> updateUser(@PathVariable ("userId") int userId,@Valid @RequestBody User user){
        User updatedUser = userService.updateUser(userId,user);
        ResponseWrapper response = new ResponseWrapper();
        if (updatedUser != null) {
            response.setStatusCode(HttpStatus.OK.value());
            response.setMessage("User updated successfully");
            response.setResponse(CustomMapper.mapUserDto(updatedUser));
            return ResponseEntity.ok(response);
        } else {
            response.setStatusCode(HttpStatus.NOT_FOUND.value());
            response.setMessage("User not found...Check Again");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    @DeleteMapping("/user/{userId}")
    private ResponseEntity<ResponseWrapper> deleteUser(@PathVariable("userId")int userId){
        userService.deleteUser(userId);
        ResponseWrapper response = new ResponseWrapper();
        response.setStatusCode(HttpStatus.OK.value());
        response.setMessage("User deleted successfully");
        return ResponseEntity.ok(response);
    }

}
