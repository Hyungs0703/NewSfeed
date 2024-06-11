package com.sparta.newsfeed.dto;

import com.sparta.newsfeed.entity.Comment;
import lombok.Getter;

@Getter

public class CommentResponseDto {
    private String comment;

    public CommentResponseDto(Comment comment) {
        this.comment = comment.getComment();
    }
}
