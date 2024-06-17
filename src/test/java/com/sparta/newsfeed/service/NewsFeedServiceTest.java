package com.sparta.newsfeed.service;

import com.sparta.newsfeed.dto.NewsFeedRequestDto;
import com.sparta.newsfeed.dto.NewsFeedResponseDto;
import com.sparta.newsfeed.entity.NewsFeed;
import com.sparta.newsfeed.entity.User;
import com.sparta.newsfeed.repository.NewsFeedRepository;
import com.sparta.newsfeed.repository.UserRepository;
import com.sparta.newsfeed.security.UserDetailsImpl;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT) // 서버의 PORT 를 랜덤으로 설정합니다.
@TestInstance(TestInstance.Lifecycle.PER_CLASS) // 테스트 인스턴스의 생성 단위를 클래스로 변경합니다.
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class NewsFeedServiceTest {

    @Autowired
    NewsFeedService newsFeedService;
    @Autowired
    UserRepository userRepository;

    User user;

    NewsFeedResponseDto createdNewsfeed = null;

    @Autowired
    private NewsFeedRepository newsFeedRepository;

    @Test
    @Order(1)
    @DisplayName("게시글 작성")
    void test1() {
        //given
        String contents = "게시글작성";
        NewsFeedRequestDto newsFeedRequestDto = new NewsFeedRequestDto(contents);

        user = userRepository.findById(1L).orElse(null);
        UserDetailsImpl userInfo = new UserDetailsImpl(user);

        //when
        NewsFeedResponseDto newsFeed = newsFeedService.createNewsFeed(newsFeedRequestDto, userInfo);

        //then
        assertNotNull(newsFeed.getContents());
        assertEquals(contents, newsFeed.getContents());
        createdNewsfeed = newsFeed;
    }

    @Test
    @Order(2)
    @DisplayName("게시글 조회")
    void test2() {
        //given
        user = userRepository.findById(1L).orElse(null);
        UserDetailsImpl userDetails = new UserDetailsImpl(user);
        //when
        NewsFeed newsFeed = newsFeedRepository.findById(userDetails.getUser().getId()).orElse(null);
        String contents = Objects.requireNonNull(newsFeed).getContents();

        //then
        assertNotNull(newsFeed);
        assertNotNull(contents);
    }

    @Test
    @Order(3)
    @DisplayName("등록된 모든 게시글 조회")
    void test3() {
        // given
        // when
        List<NewsFeedResponseDto> newsfeedList = newsFeedService.getAllNewsFeeds();

        //then
        assertNotNull(newsfeedList);
    }

    @Test
    @Order(4)
    @DisplayName("등록된 게시글 수정")
    void test4() {
        //given
        user = userRepository.findById(1L).orElse(null);
        Long newsfeedId = 2L;
        String contents = "수정합니다.";

        //when
        UserDetailsImpl userDetails = new UserDetailsImpl(user);
        NewsFeedRequestDto newsFeed = new NewsFeedRequestDto(contents);
        newsFeedService.updateNewsFeed(newsfeedId, newsFeed, userDetails);

        //then
        assertNotNull(newsFeed, "수정");
    }
    @Test
    @Order(5)
    @DisplayName("등록된 게시글 삭제")
    void test5() {
        user = userRepository.findById(1L).orElse(null);
        Long newsfeedId = 1L;

        //when
        UserDetailsImpl userDetails = new UserDetailsImpl(user);
        newsFeedService.deleteNewsFeed(newsfeedId, userDetails);

        //then
        assertNotNull(newsfeedId);
    }
}