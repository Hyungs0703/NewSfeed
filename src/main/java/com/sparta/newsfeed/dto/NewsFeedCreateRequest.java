package com.sparta.newsfeed.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NewsFeedCreateRequest {
    private String contents;
    private String username;

}