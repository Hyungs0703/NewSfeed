package com.sparta.newsfeed.controller;

import com.sparta.newsfeed.dto.NewsFeedRequestDto;
import com.sparta.newsfeed.dto.NewsFeedResponseDto;
import com.sparta.newsfeed.security.UserDetailsImpl;
import com.sparta.newsfeed.service.NewsFeedService;
import lombok.RequiredArgsConstructor;
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
    @ResponseBody
    public NewsFeedResponseDto createNewsFeed(@RequestBody NewsFeedRequestDto request, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return newsFeedService.createNewsFeed(request, userDetails.getUser());
    }

    //전체 게시물 조최 : 누구든 조회 가능
    @GetMapping("/newsfeeds")
    public ResponseEntity<List<NewsFeedResponseDto>> getNewsfeedList(){
        List<NewsFeedResponseDto> response = newsFeedService.getNewsfeedList();
        return ResponseEntity.ok(response);
    }


    //개별 게시물 조회 : 누구든 조회 가능
    @GetMapping("/newsfeeds/{id}")
    public ResponseEntity<NewsFeedResponseDto> getNewsfeed(@PathVariable Long id) {
        NewsFeedResponseDto response = newsFeedService.getNewsfeed(id);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/newsfeeds/{id}")
    public NewsFeedResponseDto updateNewsFeed (@PathVariable Long id, @RequestBody  NewsFeedRequestDto requestDto, @AuthenticationPrincipal UserDetails userDetails) {
        return newsFeedService.updateNewsFeed(id, requestDto, userDetails);
    }

    //게시물 삭제
    @DeleteMapping("/newsfeeds/{id}")
    public ResponseEntity<String> deleteNewsfeed(@PathVariable Long id) {
        newsFeedService.deleteNewsfeed(id);
        return ResponseEntity.ok("게시글이 삭제되었습니다.");
    }

}
