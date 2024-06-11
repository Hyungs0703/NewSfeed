package com.sparta.newsfeed.dto;

import lombok.Getter;

@Getter
public class CommentRequestDto {
    private Long newsfeedId;
    private String contents;
}
