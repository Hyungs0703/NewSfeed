package com.sparta.newsfeed.controller;

import com.sparta.newsfeed.dto.NewsFeedRequestDto;
import com.sparta.newsfeed.dto.NewsFeedResponseDto;
import com.sparta.newsfeed.security.UserDetailsImpl;
import com.sparta.newsfeed.service.NewsFeedService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class NewsFeedController {

    public final NewsFeedService newsFeedService;

    //게시물 작성
    @PostMapping("/newsfeeds")
    public ResponseEntity<?> createNewsFeed(@RequestBody NewsFeedRequestDto request, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        NewsFeedResponseDto newsFeedResponseDto = newsFeedService.createNewsFeed(request, userDetails);
        return ResponseEntity.status(HttpStatus.CREATED).body(newsFeedResponseDto);
    }

    //전체 게시물 조최 : 누구든 조회 가능
    @GetMapping("/newsfeeds")
    public ResponseEntity<?> getNewsfeedList() {
        List<NewsFeedResponseDto> newsfeedList = newsFeedService.getNewsfeedList();
        return ResponseEntity.status(HttpStatus.FOUND).body(newsfeedList);
    }

    //전체 게시물 조회(내림차순)
    @GetMapping("/newsfeeds/find")
    public ResponseEntity<?> getAllNewsFeeds() {
        List<NewsFeedResponseDto> newsfeedList = newsFeedService.getAllNewsFeeds();
        return ResponseEntity.status(HttpStatus.OK).body(newsfeedList);
    }

    //개별 게시물 조회 : 누구든 조회 가능
    @GetMapping("/newsfeeds/{newsfeedId}")
    public ResponseEntity<?> getNewsfeed(@Valid @PathVariable Long newsfeedId) {
        NewsFeedResponseDto newsfeed = newsFeedService.getNewsfeed(newsfeedId);
        return ResponseEntity.status(HttpStatus.OK).body(newsfeed);
    }
    //게시물 수정
    @PutMapping("/newsfeeds/{newsfeedId}")
    public ResponseEntity<?> updateNewsFeed (@PathVariable Long newsfeedId, @RequestBody NewsFeedRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        NewsFeedResponseDto getNewsFeed = newsFeedService.updateNewsFeed(newsfeedId, requestDto, userDetails);
        return ResponseEntity.status(HttpStatus.OK).body(getNewsFeed);
    }

    //게시물 삭제
    @DeleteMapping("/newsfeeds/{newsfeedId}")
    public ResponseEntity<?> deleteNewsFeed(@PathVariable Long newsfeedId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        newsFeedService.deleteNewsFeed(newsfeedId,userDetails);
        return ResponseEntity.status(HttpStatus.OK).body("삭제가 완료되었습니다");
    }


}
