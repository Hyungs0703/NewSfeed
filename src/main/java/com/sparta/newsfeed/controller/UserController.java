package com.sparta.newsfeed.controller;

import com.sparta.newsfeed.dto.SignupRequestDto;
import com.sparta.newsfeed.dto.UpdateUserRequestDto;
import com.sparta.newsfeed.entity.User;
import com.sparta.newsfeed.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.Optional;

@Controller
@ResponseBody
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    //회원가입
    @PostMapping("/signup")
    public String signup(SignupRequestDto signupRequestDto) {

        System.out.println(signupRequestDto.getUsername()+ "회원가입이 완료되었습니다.");
        userService.signup(signupRequestDto);

        return "ok";
    }
    //회원정보 수정
    @PutMapping("/profile")
    public Optional<User> updateProfile(@Valid @RequestBody UpdateUserRequestDto updateUserRequestDto) throws IOException {
       return userService.updateProfile(updateUserRequestDto);
    }




//    //탈퇴
//    @PutMapping("/secession")
//    public User secession(SecessionRequestDto secessionRequestDto) {
//        return userService.secession(secessionRequestDto);
//    }


}