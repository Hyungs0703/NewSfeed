package com.sparta.newsfeed.service;

import com.sparta.newsfeed.dto.NewsFeedRequestDto;
import com.sparta.newsfeed.dto.NewsFeedResponseDto;
import com.sparta.newsfeed.entity.NewsFeed;
import com.sparta.newsfeed.entity.User;
import com.sparta.newsfeed.repository.NewsFeedRepository;
import com.sparta.newsfeed.repository.UserRepository;
import com.sparta.newsfeed.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
        NewsFeed newsFeed = findNewsFeedById(newsFeedId);

        return new NewsFeedResponseDto(newsFeed);
    }

    private NewsFeed findNewsFeedById(Long newsFeedId) {
        return newsFeedRepository.findById(newsFeedId)
                .orElseThrow(() -> new IllegalArgumentException("NewsFeed에서 해당 ID를 찾을 수 없습니다: " + newsFeedId));

        //인가 if 추가부분
    }

    @Transactional
    public NewsFeedResponseDto updateNewsFeed(Long newsFeedId, NewsFeedRequestDto requestDto, UserDetailsImpl userDetails) {

        String username = userDetails.getUsername();


        Optional<NewsFeed> optionalNewsFeed = newsFeedRepository.findById(newsFeedId);


        NewsFeed newsFeed = optionalNewsFeed.orElseThrow(()-> new IllegalArgumentException("해당 ID로 뉴스피드 찾을 수 없습니다."));


        User user = newsFeed.getUser();

        if(!username.equals(user.getUsername())) {

            throw new SecurityException("수정 권한이 없습니다.");

        }

        newsFeed.setContents(requestDto.getContents());
        newsFeedRepository.save(newsFeed);


        return new NewsFeedResponseDto(newsFeed);

    }


    //삭제
    public ResponseEntity<String> deleteNewsFeed(Long id, UserDetailsImpl userDetails) {

        NewsFeed newsFeed = findById(id);
        if (!newsFeed.getUser().getId().equals(userDetails.getUser().getId())) {
            throw new IllegalArgumentException("작성자 외 다른 사용자는 삭제할 수 없습니다.");
        }

        newsFeedRepository.delete(newsFeed);
        return ResponseEntity.ok("게시물이 삭제되었습니다.");
    }

    public NewsFeed findById(Long id) {
        return newsFeedRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("선택한 게시물이 존재하지 않습니다.")
                );
    }

    // 뉴스피드 조회 기능 : 뉴스피드가 없을 경우 메세지 표시
    public List<NewsFeedResponseDto> getAllNewsFeeds() {
        List<NewsFeed> newsFeeds = newsFeedRepository.findAllByOrderByCreatedAtDesc();
        return newsFeeds.stream()
                .map(NewsFeedResponseDto::new)
                .collect(Collectors.toList());
    }
}
