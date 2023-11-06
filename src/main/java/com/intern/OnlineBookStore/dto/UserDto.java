package com.intern.OnlineBookStore.dto;

import lombok.Data;

@Data
public class UserDto {

    private String username;
    private String email;
    private String role;

    //Used instead of @NoArgsConstructor
    public UserDto() {
    }

    //Used instead of @AllArgsConstructor

    public UserDto(String username, String email, String role) {
        this.username = username;
        this.email = email;
        this.role = role;
    }
}

