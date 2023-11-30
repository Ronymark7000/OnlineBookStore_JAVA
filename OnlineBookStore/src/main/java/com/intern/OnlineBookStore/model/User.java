package com.intern.OnlineBookStore.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

import javax.naming.Name;

@Getter
@Setter
@Table(name = "onlineuser")
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private int userId;

    @Column(nullable = false, length = 30, unique = true)
    @NotEmpty
    @Size(min =3, message = "Must have Atleast 3 Character")
    @Pattern(regexp = "^[a-zA-Z0-9_-]{3,20}$", message = "Username should not contain space")
    private String username;

    @Column(nullable = false, length = 50, unique = true)
    @Email(message = "Invalid email format")
    private String email;

    @Column(nullable = false, length = 100)
    @NotEmpty
    @Size(min = 5, message = "Atleast 5 character long")
    private String password;

    @Column(nullable = false, length = 10)
//    @NotBlank(message = "Role is mandatory")
    private String role;
}





