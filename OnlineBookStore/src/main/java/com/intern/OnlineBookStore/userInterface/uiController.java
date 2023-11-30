package com.intern.OnlineBookStore.userInterface;

import com.intern.OnlineBookStore.config.SecurityConfig;
import com.intern.OnlineBookStore.dto.UserDto;
import com.intern.OnlineBookStore.model.User;
import com.intern.OnlineBookStore.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller

public class uiController {



        @Autowired
        private UserService userService;
        private SecurityConfig securityConfig;

        public uiController(UserService userService) {
            this.userService = userService;
        }

        /*@GetMapping("/login")
        public String ....(){
            return "login";
        }*/
        @GetMapping("/")
        public String userIndex() {
            return "userIndex";
        }
        @GetMapping("/allUsers")
        public String getAllUsers(Model model) {
            List<UserDto> user = userService.getAllUsers();
            model.addAttribute("user", user);
            return "listUser";
        }

        @GetMapping("/addUser")
        public String createUser(Model model){
            User user = new User();
            model.addAttribute("user",user);
            return "addUser";
        }
        @PostMapping("/save")
        public String saveUser(@Valid @ModelAttribute ("user")User user, BindingResult result){
            if (result.hasErrors()){
                return "addUser";
            }
            userService.createUser(user);
            return "redirect:/allUsers";
        }

        @GetMapping("/update/{id}")
        public String updateUser(@Valid @PathVariable ("id")int userId, Model model){
            UserDto user = userService.getUserById(userId);
            model.addAttribute("user",user);
            return "updateUser";
        }
        @PostMapping("/update/{id}")
        public String update(@PathVariable("id")int userId,@Valid @ModelAttribute ("user")User user, BindingResult result){
            if (result.hasErrors()){
                return "updateUser";
            }
            userService.updateUser(userId, user);
            return "redirect:/allUsers";
        }

        @GetMapping("/deleteUser/{id}")
        private String deleteById(@PathVariable("id")int userId){
            userService.deleteUser(userId);
            return "redirect:/allUsers";
        }



}
