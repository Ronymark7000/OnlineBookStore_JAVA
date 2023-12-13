package com.intern.OnlineBookStore.controller;

import com.intern.OnlineBookStore.dto.UserDto;
import com.intern.OnlineBookStore.service.UserService;
import com.intern.OnlineBookStore.util.JwtService;
import com.intern.OnlineBookStore.util.ResponseWrapper;
import com.intern.OnlineBookStore.model.User;
import com.intern.OnlineBookStore.util.CustomMapper;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserService userService;


    public UserController(UserService userService) {
        this.userService = userService;
    }


    @PostMapping("/auth/register")
    private ResponseWrapper registerUser(@Valid @RequestBody User user) {
        user.setRole("user");
        return  userService.createUser(user);
    }



    @PostMapping("/auth/login")
    private ResponseWrapper loginUser(@Valid @RequestBody User users, HttpServletResponse response) {
        User user = userService.logIn(users.getUsername(), users.getPassword());

        if(user != null){
            JwtService jwtService = new JwtService();
            String token = jwtService.generateToken(user);
            final Cookie cookie = new Cookie("token", token);
            cookie.setSecure(false);
            cookie.setHttpOnly(true);
            cookie.setMaxAge(50400);
            cookie.setPath("/api");
            response.addCookie(cookie);
            return new ResponseWrapper(true, 200, "Login Success", user);
        }else{
            return new ResponseWrapper(false, 400, "User not found ", "User Not Logged In");
        }
    }

    @GetMapping("/admin/users")
    private ResponseWrapper getAllUsers() {
        List<UserDto> user = userService.getAllUsers();
        ResponseWrapper response = new ResponseWrapper();
        response.setStatusCode(HttpStatus.OK.value());
        response.setMessage("Users retrieved successfully");
        response.setResponse(user);
        return response;
    }

    @GetMapping("/admin/user/{userId}")
    private ResponseWrapper getUserById(@PathVariable ("userId") int userId) {

        UserDto user = userService.getUserById(userId);
        if (user != null) {
            ResponseWrapper response = new ResponseWrapper();
            response.setStatusCode(HttpStatus.OK.value());
            response.setMessage("Users retrieved successfully by ID");
            response.setResponse(userService.getUserById(userId));
            return response;
        } else {
            ResponseWrapper response = new ResponseWrapper();
            response.setStatusCode(HttpStatus.NOT_FOUND.value());
            response.setMessage("User not found...Please Check Again");
            return response;
        }
    }

    @GetMapping("/profile")
    private ResponseWrapper getProfile(HttpServletRequest request) {
        int userId = (int) request.getAttribute("userId");
        System.out.println("Id " + userId);
        UserDto user = userService.getUserById(userId);
        if (user != null) {
            ResponseWrapper response = new ResponseWrapper();
            response.setStatusCode(HttpStatus.OK.value());
            response.setMessage("User retrieved successfully");
            response.setSuccess(true);
            response.setResponse(user);
            return response;
        } else {
            ResponseWrapper response = new ResponseWrapper();
            response.setStatusCode(HttpStatus.NOT_FOUND.value());
            response.setMessage("User not found");
            response.setSuccess(false);
            return response;
        }
    }

    @PostMapping("/admin/users")
    private ResponseWrapper createUser(@Valid @RequestBody User user){
//      userService.createUser(user);
        ResponseWrapper response = new ResponseWrapper();
        response.setStatusCode(HttpStatus.CREATED.value());
        response.setMessage("Successfully Registered where User Id: " + user.getUserId() );
        response.setResponse(userService.createUser(user));
        return response;
    }

    @PutMapping("/admin/user/{userId}")
    private ResponseWrapper updateUser(@PathVariable ("userId") int userId,@Valid @RequestBody User user){
        User updatedUser = userService.updateUser(userId,user);
        ResponseWrapper response = new ResponseWrapper();
        if (updatedUser != null) {
            response.setStatusCode(HttpStatus.OK.value());
            response.setMessage("User updated successfully");
            response.setResponse(CustomMapper.mapUserDto(updatedUser));
            return response;
        } else {
            response.setStatusCode(HttpStatus.NOT_FOUND.value());
            response.setMessage("User not found...Check Again");
            return response;
        }
    }

    @DeleteMapping("/admin/user/{userId}")
    private ResponseWrapper deleteUser(@PathVariable("userId")int userId){
        userService.deleteUser(userId);
        ResponseWrapper response = new ResponseWrapper();
        response.setStatusCode(HttpStatus.OK.value());
        response.setMessage("User deleted successfully");
        return response;
    }

}
