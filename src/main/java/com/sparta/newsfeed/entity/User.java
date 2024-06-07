package com.sparta.newsfeed.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table
public class User extends TimeStamped{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    //사용자 ID
    private String username;
    //사용자 비밀번호
    private String password;
    //이름
    private String name;
    //이메일
    private String email;
    //한줄소개
    @Length(min = 5, max = 20)
    private String introduce;
    private String role;

    public User(String username, String password, String name, String email, String introduce, String role) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.email = email;
        this.introduce = introduce;
        this.role = role;
    }

}


