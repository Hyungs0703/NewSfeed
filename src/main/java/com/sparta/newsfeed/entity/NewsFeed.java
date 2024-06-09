package com.sparta.newsfeed.entity;

import com.sparta.newsfeed.dto.NewsFeedRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class NewsFeed extends TimeStamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(columnDefinition = "TEXT")
    private String contents;



    public NewsFeed(NewsFeedRequestDto request, User user) {
        this.user = user;
        this.contents = request.getContents();
    }

    public void update(NewsFeedRequestDto newsFeedRequestDto) {
        this.contents = newsFeedRequestDto.getContents();
    }
}