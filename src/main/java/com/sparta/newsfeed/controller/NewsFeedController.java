package com.sparta.newsfeed.controller;

import com.sparta.newsfeed.dto.NewsFeedRequestDto;
import com.sparta.newsfeed.dto.NewsFeedResponseDto;
import com.sparta.newsfeed.security.UserDetailsImpl;
import com.sparta.newsfeed.service.NewsFeedService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

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

//    //전체 게시물 조최 : 누구든 조회 가능
//    @GetMapping
//    public ResponseEntity<List<NewsFeedResponseDto>> findAll(){
//        List<NewsFeedResponseDto> response = newsFeedService.findAll();
//        return ResponseEntity.ok(response);
//    }
//
//    //개별 게시물 조회 : 누구든 조회 가능
//    @GetMapping("/{newsFeedId}")
//    public ResponseEntity<NewsFeedResponseDto> findByID(@PathVariable(name = "newsFeedId") Long newsFeedId) {
//        NewsFeedResponseDto response = newsFeedService.findById(newsFeedId);
//        return ResponseEntity.ok(response);
//    }
//
////U
//
//    @DeleteMapping("/{newsFeedId}")
//    public ResponseEntity<String> deleteByID(@PathVariable(name = "newsFeedId") Long newsFeedId) {
//        newsFeedService.delete(newsFeedId);
//        return ResponseEntity.ok("게시글이 삭제되었습니다.");
//    }
}
