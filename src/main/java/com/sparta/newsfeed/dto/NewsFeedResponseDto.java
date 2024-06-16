package com.sparta.newsfeed.dto;

import com.sparta.newsfeed.entity.NewsFeed;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class NewsFeedResponseDto {
    private String contents;

    public NewsFeedResponseDto(NewsFeed newsFeed) {
        this.contents=newsFeed.getContents();
    }
}
