package com.sparta.newsfeed.entity;

import com.sparta.newsfeed.dto.NewsFeedCreateRequest;
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

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn (name = "userId")
    private User user;

    @Column(columnDefinition = "TEXT")
    private String contents;



    public NewsFeed(NewsFeedCreateRequest request, User user) {
        super();
    }
}