package com.sparta.newsfeed.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UpdateUserRequestDto {
    String username;
    String password;
    String introduce;
}