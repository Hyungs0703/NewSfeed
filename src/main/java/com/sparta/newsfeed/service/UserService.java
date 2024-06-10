package com.sparta.newsfeed.service;

import com.sparta.newsfeed.dto.LoginRequestDto;
import com.sparta.newsfeed.dto.SignupRequestDto;
import com.sparta.newsfeed.dto.UpdateInfoRequestDto;
import com.sparta.newsfeed.dto.WithdrawalRequestDto;
import com.sparta.newsfeed.entity.User;
import com.sparta.newsfeed.entity.UserRoleEnum;
import com.sparta.newsfeed.repository.UserRepository;
import com.sparta.newsfeed.security.UserDetailsImpl;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    // 회원탈퇴 토큰
    private final String BLACKLIST_TOKEN = "AAABnvxRVklrnYxKZ0aHgTBcXukeZygoC";

    //회원가입
    public void signup(SignupRequestDto requestDto) {
        String username = requestDto.getUsername();
        String password = passwordEncoder.encode(requestDto.getPassword());
        String name = requestDto.getName();
        String introduce = requestDto.getIntroduce();
        String refreshToken = requestDto.getRefreshToken();

        // 회원 중복 확인
        Optional<User> checkUsername = userRepository.findByUsername(username);
        if (checkUsername.isPresent()) {
            throw new IllegalArgumentException("중복된 사용자가 존재합니다.");
        }

        // email 중복확인
        String email = requestDto.getEmail();
        Optional<User> checkEmail = userRepository.findByEmail(email);
        if (checkEmail.isPresent()) {
            throw new IllegalArgumentException("중복된 Email 입니다.");
        }

        // 사용자 상태 확인
        UserRoleEnum role = UserRoleEnum.USER;
        if (requestDto.isRole()) {
            if (!BLACKLIST_TOKEN.equals(requestDto.getRole())) {
                throw new IllegalArgumentException("회원 탈퇴한 아이디입니다.");
            }
            role = UserRoleEnum.USER;
        }

        // 사용자 등록
        User user = new User(username, password, name, email, introduce, role, refreshToken);


        userRepository.save(user);
    }

    //회원 정보
    public Optional<User> getUserInfo(LoginRequestDto requestDto) {
        return userRepository.findByUsername(requestDto.getUsername());
    }

    //회원정보수정
    @Transactional
    public ResponseEntity<Optional<User>> updateUserInfo(UpdateInfoRequestDto requestDto, UserDetailsImpl userDetails) {
        User user = userDetails.getUser();
        String password = passwordEncoder.encode(requestDto.getPassword());
        String introduce = requestDto.getIntroduce();
        Optional<User> checkUsername = userRepository.findByUsername(requestDto.getUsername());
        Optional<User> checkPassword = userRepository.findByPassword(password);
        if (checkUsername.isPresent()|| checkPassword.isPresent()) {
            user.setIntroduce(introduce);
        }
        userRepository.save(user);
        return ResponseEntity.ok().body(checkUsername);
    }

    @Transactional
    public void withdrawal(WithdrawalRequestDto requestDto) {

    }

    public void logout(LoginRequestDto requestDto, UserDetailsImpl userDetails) {

    }



}