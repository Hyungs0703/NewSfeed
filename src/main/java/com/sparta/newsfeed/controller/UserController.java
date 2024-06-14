package com.sparta.newsfeed.controller;

import com.sparta.newsfeed.dto.JoinRequestDto;
import com.sparta.newsfeed.dto.SignupRequestDto;
import com.sparta.newsfeed.dto.UpdateInfoRequestDto;
import com.sparta.newsfeed.dto.UserInfoResponseDto;
import com.sparta.newsfeed.entity.User;
import com.sparta.newsfeed.security.UserDetailsImpl;
import com.sparta.newsfeed.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    //회원가입
    @PostMapping("/signup")
    public ResponseEntity<User> signup(@RequestBody SignupRequestDto requestDto) {
        userService.signup(requestDto);
        return ResponseEntity.ok().build();
    }
    // 회원 관련 정보 받기
    @GetMapping("/user-info")
    public Optional<User> getUserInfo(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return userService.getUserInfo(userDetails);
    }

    //회원 소개 수정
    @PutMapping("/user-info")
    public UserInfoResponseDto updateUserInfo(@RequestBody UpdateInfoRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
       return userService.updateUserInfo(requestDto, userDetails);
    }

    //회원탈퇴
    @PutMapping("/withdrawal")
    public void withdrawal(@RequestBody JoinRequestDto requestDto,@AuthenticationPrincipal UserDetailsImpl userDetails) {
        userService.withdrawal(requestDto, userDetails);
    }

    //로그아웃
    @DeleteMapping("/logout")
    public void logout(@RequestBody JoinRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        userService.logout(requestDto, userDetails);
    }

}