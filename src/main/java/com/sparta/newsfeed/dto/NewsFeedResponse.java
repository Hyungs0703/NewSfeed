package com.sparta.newsfeed.dto;

import com.sparta.newsfeed.entity.NewsFeed;
import com.sparta.newsfeed.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class NewsFeedResponse {
    private Long id;
    private User username;
    private String contents;

    public NewsFeedResponse(NewsFeed newsFeed) {
        this.id = newsFeed.getId();
        this.contents=newsFeed.getContents();
        this.username=newsFeed.getUser();
    }
}
