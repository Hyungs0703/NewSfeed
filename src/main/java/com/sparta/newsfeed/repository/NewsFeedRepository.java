package com.sparta.newsfeed.repository;

import com.sparta.newsfeed.entity.NewsFeed;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NewsFeedRepository extends JpaRepository<NewsFeed, Long> {
    List<NewsFeed> findAllByOrderByCreatedAtDesc();

}

