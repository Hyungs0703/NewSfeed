package com.sparta.newsfeed.dto;

import com.sparta.newsfeed.entity.User;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class JoinRequestDto {
    private String username;
    private String password;
}
