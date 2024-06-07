package com.sparta.newsfeed.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SignupRequestDto {

    private String username;
    private String password;
    private String name;
    private String email;
    private String introduce;
    private boolean admin = false;
    private String adminToken = "";
}