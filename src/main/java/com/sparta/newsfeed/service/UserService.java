package com.sparta.newsfeed.service;

import com.sparta.newsfeed.dto.JoinRequestDto;
import com.sparta.newsfeed.dto.SignupRequestDto;
import com.sparta.newsfeed.dto.UpdateInfoRequestDto;
import com.sparta.newsfeed.dto.UserInfoResponseDto;
import com.sparta.newsfeed.entity.User;
import com.sparta.newsfeed.entity.UserRoleEnum;
import com.sparta.newsfeed.repository.UserRepository;
import com.sparta.newsfeed.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
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

    // 회원가입
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

    // 회원 정보
    public Optional<User> getUserInfo(UserDetailsImpl userDetails) {
        return userRepository.findByUsername(userDetails.getUsername());
    }

    // 회원 프로필 수정
    public UserInfoResponseDto updateUserInfo(UpdateInfoRequestDto requestDto, UserDetailsImpl userDetails) {
        validateUserDetailsAndRequest(userDetails, requestDto);

        User user = userDetails.getUser();
        String introduce = requestDto.getIntroduce();
        String rawPassword = requestDto.getPassword();
        String encodedPassword = user.getPassword();

        // 해당 비밀번호가 맞는지 조회
        if (passwordEncoder.matches(rawPassword, encodedPassword)) {
            user.setIntroduce(introduce);
            userRepository.save(user);
        } else {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        return new UserInfoResponseDto(
                userDetails.getUsername(),
                user.getName(),
                userDetails.getEmail(),
                user.getIntroduce());
    }

    // 회원탈퇴
    public void withdrawal(JoinRequestDto requestDto, UserDetailsImpl userDetails) {
        findUser(requestDto, userDetails);

        User user = userDetails.getUser();
        // 사용자 탈퇴 처리
        user.setToken(BLACKLIST_TOKEN);
        user.setRole(UserRoleEnum.WITHDRAWAL);
        userRepository.save(user);
    }

    // 로그아웃
    public void logout(JoinRequestDto requestDto, UserDetailsImpl userDetails) {
        findUser(requestDto, userDetails);

        User user = userDetails.getUser();
        // 사용자 로그아웃 처리
        user.setToken("");
        userRepository.save(user);
    }

    private void validateUserDetailsAndRequest(UserDetailsImpl userDetails, Object requestDto) {
        if (userDetails == null || requestDto == null) {
            throw new IllegalArgumentException("해당 데이터는 존재하지 않습니다.");
        }

        User user = userDetails.getUser();
        if (user == null) {
            throw new IllegalStateException("해당 유저는 존재하지 않습니다.");
        }
    }

    private void findUser(JoinRequestDto requestDto, UserDetailsImpl userDetails) {
        validateUserDetailsAndRequest(userDetails, requestDto);

        User user = userDetails.getUser();
        // 요청된 사용자 이름과 현재 로그인한 사용자가 일치하는지 확인
        if (!user.getUsername().equals(requestDto.getUsername())) {
            throw new IllegalArgumentException("해당 유저의 이름이 일치하지 않습니다.");
        }

        // 비밀번호 확인
        String rawPassword = requestDto.getPassword();
        if (!passwordEncoder.matches(rawPassword, user.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }
    }
}
