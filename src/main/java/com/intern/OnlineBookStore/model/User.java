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
    private Integer userId;

    @Column(nullable = false, length = 30)
    @NotEmpty
    @Size(min =3, message = "Must have Atleast 3 Character")
    @Pattern(regexp = "^[a-zA-Z0-9_-]{3,20}$", message = "Username should not contain space")
    private String username;

    @Column(nullable = false, length = 50)
    @Email(message = "Invalid email format")
    private String email;

    @Column(nullable = false, length = 15)
    @NotEmpty
    @Size(min = 5, message = "Atleast 5 character long")
    private String password;

    @Column(nullable = false, length = 10)
    @NotBlank(message = "Role is mandatory")
    @Pattern(regexp = "^(student|teach)$" ,message = "Roles are invalid")
    private String role;
}





