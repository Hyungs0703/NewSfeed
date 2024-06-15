package com.sparta.newsfeed.dto;

import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.util.MultiValueMap;

import java.security.Principal;

@Getter
@Setter
@AllArgsConstructor
@Builder
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
