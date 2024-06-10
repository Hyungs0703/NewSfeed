package com.sparta.newsfeed.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
public class UserInfoDto {
    String username;
    String name;
    String email;
    String introduce;
    boolean role;
}