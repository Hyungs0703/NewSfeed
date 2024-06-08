package com.sparta.newsfeed.service;

import com.sparta.newsfeed.dto.NewsFeedCreateRequest;
import com.sparta.newsfeed.dto.NewsFeedResponse;
import com.sparta.newsfeed.entity.NewsFeed;
import com.sparta.newsfeed.entity.User;
import com.sparta.newsfeed.repository.NewsFeedRepository;
import com.sparta.newsfeed.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NewsFeedService {
    private final NewsFeedRepository newsFeedRepository;
    private final UserRepository userRepository;

    public NewsFeedResponse createNewsFeed(NewsFeedCreateRequest request, User user) {
        NewsFeed newsFeed = newsFeedRepository.save(new NewsFeed(request, user));
        return new NewsFeedResponse(newsFeed);
    }
//    //단일 게시글 조회
//    public NewsFeedResponse findById(Long newsFeedId) {
//        NewsFeed newsFeed = findNewsFeedById(newsFeedId);
//
//        return new NewsFeedResponse(newsFeed.getId(), newsFeed.getContents());
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
//    public List<NewsFeedResponse> findAll() {
//        List<NewsFeed> newsFeeds = newsFeedRepository.findAll();
//        List<NewsFeedResponse> newsFeedResponseList = new ArrayList<>();
//        for (NewsFeed newsFeed : newsFeeds) {
//            NewsFeedResponse response = new NewsFeedResponse(newsFeed.getId(), newsFeed.getContents());
//            newsFeedResponseList.add(response);
//        }
//        return newsFeedResponseList;
//    }


}
