package com.sparta.newsfeed.service;

import com.sparta.newsfeed.dto.NewsFeedRequestDto;
import com.sparta.newsfeed.dto.NewsFeedResponseDto;
import com.sparta.newsfeed.entity.NewsFeed;
import com.sparta.newsfeed.entity.User;
import com.sparta.newsfeed.repository.NewsFeedRepository;
import com.sparta.newsfeed.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NewsFeedService {
    private final NewsFeedRepository newsFeedRepository;
    private final UserRepository userRepository;

    //저장기능 구현
    public NewsFeedResponseDto createNewsFeed(NewsFeedRequestDto request, User user) {
        NewsFeed newsFeed = newsFeedRepository.save(new NewsFeed(request, user));
        return new NewsFeedResponseDto(newsFeed);
    }
//    //단일 게시글 조회
//    public NewsFeedResponseDto findById(Long newsFeedId) {
//        NewsFeed newsFeed = findNewsFeedById(newsFeedId);
//
//        return new NewsFeedResponseDto(newsFeed.getId(), newsFeed.getContents());
//    }
//
//    private NewsFeed findNewsFeedById(Long newsFeedId) {
//        return newsFeedRepository.findById(newsFeedId)
//                .orElseThrow(() -> new IllegalArgumentException("NewsFeed에서 해당 ID를 찾을 수 없습니다: " + newsFeedId));
//
//        //인가 if 추가부분
//    }
//
//    public void delete(Long newsFeedId) {
//        newsFeedRepository.deleteById(newsFeedId);
//    }
//
    public List<NewsFeedResponseDto> findAll() {
        List<NewsFeed> newsFeeds = newsFeedRepository.findAll();
        List<NewsFeedResponseDto> newsFeedResponseList = new ArrayList<>();
        for (NewsFeed newsFeed : newsFeeds) {
            NewsFeedResponseDto response = new NewsFeedResponseDto(newsFeed);
            newsFeedResponseList.add(response);
        }
        return newsFeedResponseList;
    }


}
