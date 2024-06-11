package com.sparta.newsfeed.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "users")
public class User extends TimeStamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    @Column(unique = true)
    private String username;
    @NotBlank
    private String password;
    private String name;
    @Email
    @Column(unique = true)
    private String email;
    @Length(min = 6, max = 20)
    private String introduce;
    private UserRoleEnum role;
    private String token;
    private LocalDateTime lastLogin;


    public User(String username, String password, String name, String email, String introduce, UserRoleEnum role, String token) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.email = email;
        this.introduce = introduce;
        this.role = role;
        this.token = token;
        this.lastLogin = LocalDateTime.now();
    }

}


