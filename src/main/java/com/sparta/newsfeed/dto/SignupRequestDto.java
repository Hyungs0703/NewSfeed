package com.sparta.newsfeed.dto;

import jakarta.validation.constraints.Email;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignupRequestDto {
    private String username;
    private String password;
    private String name;
    @Email
    private String email;
    private String introduce;
    private boolean isRole = false;
    private String role = "";
    private String refreshToken = "";
}
