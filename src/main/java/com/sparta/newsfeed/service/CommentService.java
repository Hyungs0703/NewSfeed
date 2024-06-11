package com.sparta.newsfeed.service;

import com.sparta.newsfeed.dto.CommentRequestDto;
import com.sparta.newsfeed.dto.CommentResponseDto;
import com.sparta.newsfeed.entity.Comment;
import com.sparta.newsfeed.entity.NewsFeed;
import com.sparta.newsfeed.repository.CommentRepository;
import com.sparta.newsfeed.repository.NewsFeedRepository;
import com.sparta.newsfeed.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final NewsFeedRepository newsFeedRepository;
    private final CommentRepository commentRepository;

    public CommentResponseDto createComment(CommentRequestDto commentRequestDto, UserDetailsImpl userDetails) {
        NewsFeed newsFeed = newsFeedRepository.findById(commentRequestDto.getNewsfeedId())
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글은 존재하지 않습니다: " + commentRequestDto.getNewsfeedId()));

        // Comment 객체를 생성하고 저장
        Comment comment = new Comment(newsFeed, userDetails.getUser(), commentRequestDto.getContents());
        commentRepository.save(comment);
        return new CommentResponseDto(comment);
    }
}
