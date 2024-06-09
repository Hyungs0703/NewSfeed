package com.sparta.newsfeed.controller;

import com.sparta.newsfeed.dto.LoginRequestDto;
import com.sparta.newsfeed.dto.SignupRequestDto;
import com.sparta.newsfeed.dto.UpdateInfoRequestDto;
import com.sparta.newsfeed.dto.WithdrawalRequestDto;
import com.sparta.newsfeed.entity.User;
import com.sparta.newsfeed.security.UserDetailsImpl;
import com.sparta.newsfeed.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    //회원가입
    @PostMapping("/signup")
    public ResponseEntity<User> signup(SignupRequestDto requestDto) {
        userService.signup(requestDto);
        return ResponseEntity.ok().build();
    }

    // 회원 관련 정보 받기
    @GetMapping("/user-info")
    @ResponseBody
    public Optional<User> getUserInfo(LoginRequestDto requestDto) {
        return userService.getUserInfo(requestDto);
    }
    //회원 소개 수정
    @PutMapping("/user-info")
    @ResponseBody
    public ResponseEntity<String> updateUserInfo(@RequestBody UpdateInfoRequestDto requestDto) {
       return userService.updateUserInfo(requestDto);
    }

    //회원탈퇴
    @PutMapping("/withdrawal")
    @ResponseBody
    public void withdrawal(@RequestBody WithdrawalRequestDto requestDto) {
        userService.withdrawal(requestDto);
    }
    @PostMapping("/logout")
    @ResponseBody
    public void logout(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        userService.logout(userDetails);
    }

}