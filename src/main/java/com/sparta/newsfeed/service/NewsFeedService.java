package com.sparta.newsfeed.service;

import com.sparta.newsfeed.dto.NewsFeedRequestDto;
import com.sparta.newsfeed.dto.NewsFeedResponseDto;
import com.sparta.newsfeed.entity.NewsFeed;
import com.sparta.newsfeed.entity.User;
import com.sparta.newsfeed.repository.NewsFeedRepository;
import com.sparta.newsfeed.repository.UserRepository;
import com.sparta.newsfeed.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NewsFeedService {

    private final NewsFeedRepository newsFeedRepository;


    //저장기능 구현
    public NewsFeedResponseDto createNewsFeed(NewsFeedRequestDto request, UserDetailsImpl userDetails) {
        NewsFeed newsFeed = newsFeedRepository.save(new NewsFeed(request, userDetails.getUser()));
        return new NewsFeedResponseDto(newsFeed);
    }

    //전체 게시물 조최 : 누구든 조회 가능
    public List<NewsFeedResponseDto> getNewsfeedList() {
        List<NewsFeed> newsFeeds = newsFeedRepository.findAll();
        List<NewsFeedResponseDto> newsFeedResponseList = new ArrayList<>();
        for (NewsFeed newsFeed : newsFeeds) {
            NewsFeedResponseDto response = new NewsFeedResponseDto(newsFeed);
            newsFeedResponseList.add(response);
        }
        return newsFeedResponseList;
    }

    //개별 게시물 조회 : 누구든 조회 가능
    public NewsFeedResponseDto getNewsfeed(Long newsFeedId) {
        NewsFeed newsFeed = newsFeedRepository.findById(newsFeedId).orElseThrow(() ->
                new IllegalArgumentException("해당 게시글은 존재하지 않습니다"));

        return new NewsFeedResponseDto(newsFeed);
    }

    @Transactional
    public NewsFeedResponseDto updateNewsFeed(Long newsFeedId, NewsFeedRequestDto requestDto, UserDetailsImpl userDetails) {
        NewsFeed newsFeed = newsFeedRepository.findById(newsFeedId).orElseThrow(() ->
                new IllegalArgumentException("해당 게시글은 존재하지 않습니다"));
        String content = requestDto.getContents();
        User user = userDetails.getUser();

        newsFeed.update(user, content);
        newsFeedRepository.save(newsFeed);
        return new NewsFeedResponseDto(newsFeed);
    }


    //삭제
    public void deleteNewsFeed(Long newsFeedId, UserDetailsImpl userDetails) {
        NewsFeed newsFeed = newsFeedRepository.findById(newsFeedId).orElseThrow(() ->
                new IllegalArgumentException("해당 게시글은 존재하지 않습니다"));

        if (!newsFeed.getUser().getId().equals(userDetails.getUser().getId())) {
            throw new IllegalArgumentException("작성자 외 다른 사용자는 삭제할 수 없습니다.");
        }

        newsFeedRepository.delete(newsFeed);
    }


    // 뉴스피드 조회 기능 : 뉴스피드가 없을 경우 메세지 표시
    public List<NewsFeedResponseDto> getAllNewsFeeds() {
        List<NewsFeed> newsFeeds = newsFeedRepository.findAllByOrderByCreatedAtDesc();
        return newsFeeds.stream()
                .map(NewsFeedResponseDto::new)
                .collect(Collectors.toList());
    }
}
