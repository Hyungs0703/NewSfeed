package com.sparta.newsfeed.controller;

import com.sparta.newsfeed.dto.JoinRequestDto;
import com.sparta.newsfeed.dto.SignupRequestDto;
import com.sparta.newsfeed.dto.UpdateInfoRequestDto;
import com.sparta.newsfeed.dto.UserInfoResponseDto;
import com.sparta.newsfeed.security.UserDetailsImpl;
import com.sparta.newsfeed.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    //회원가입
    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody SignupRequestDto requestDto) {
        userService.signup(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body("회원 가입이 완료되었습니다.");
    }

    // 회원 관련 정보 받기
    @GetMapping("/user-info")
    public ResponseEntity<?> getUserInfo(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        UserInfoResponseDto userInfoResponseDto = userService.getUserInfo(userDetails);
        return ResponseEntity.status(HttpStatus.FOUND).body(userInfoResponseDto);
    }

    //회원 소개 수정
    @PutMapping("/user-info")
    public ResponseEntity<?> updateUserInfo(@RequestBody UpdateInfoRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        UserInfoResponseDto userInfoResponseDto = userService.updateUserInfo(requestDto,userDetails);
        return ResponseEntity.status(HttpStatus.UPGRADE_REQUIRED).body(userInfoResponseDto);
    }

    //로그아웃
    @DeleteMapping("/logout")
    public ResponseEntity<?> logout(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        userService.logout(userDetails);
        return ResponseEntity.status(HttpStatus.OK).body("로그 아웃 되었습니다.");
    }

    //회원탈퇴
    @PutMapping("/withdrawal")
    public ResponseEntity<?> withdrawal(@RequestBody JoinRequestDto requestDto,@AuthenticationPrincipal UserDetailsImpl userDetails) {
        userService.withdrawal(requestDto, userDetails);
        return ResponseEntity.status(HttpStatus.OK).body("회원 탈퇴가 완료 되었습니다.");
    }
}