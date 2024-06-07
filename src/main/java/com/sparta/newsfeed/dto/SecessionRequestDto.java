package com.sparta.newsfeed.dto;

import lombok.Getter;

@Getter
public class SecessionRequestDto {
    String username;
    String password;
    String role = "secession";
}
