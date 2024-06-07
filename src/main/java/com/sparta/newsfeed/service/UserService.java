package com.sparta.newsfeed.service;

import com.sparta.newsfeed.dto.SignupRequestDto;
import com.sparta.newsfeed.dto.UpdateUserRequestDto;
import com.sparta.newsfeed.entity.User;
import com.sparta.newsfeed.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Transactional
    public void signup(SignupRequestDto signupRequestDto) {

        String username = signupRequestDto.getUsername();
        String password = bCryptPasswordEncoder.encode(signupRequestDto.getPassword());
        String name = signupRequestDto.getName();
        String introduce = signupRequestDto.getIntroduce();

        //사용자 아이디 중복확인
        Optional<User> checkUsername = userRepository.findByUsername(username);
        if (checkUsername.isPresent()) {
            throw new IllegalArgumentException("중복된 사용자 아이디가 존재합니다.");
        }
        // email 중복확인
        String email = signupRequestDto.getEmail();
        Optional<User> checkEmail = userRepository.findByEmail(email);
        if (checkEmail.isPresent()) {
            throw new IllegalArgumentException("중복된 Email 입니다.");
        }

        String role = "USER";
        User data = new User(username, password, name, email, introduce, role);
        userRepository.save(data);
    }

    @Transactional
    public Optional<User> updateProfile(UpdateUserRequestDto updateUserRequestDto) throws IOException {
        String username = updateUserRequestDto.getUsername();
        String password = updateUserRequestDto.getPassword();
        String introduce = updateUserRequestDto.getIntroduce();

        Optional<User> checkUserdata = userRepository.findByUsername(username);

        if (checkUserdata.isPresent()) {
            User user = checkUserdata.get();
            bCryptPasswordEncoder.matches(password, user.getPassword());
            if(!bCryptPasswordEncoder.matches(password, user.getPassword())){
                throw new IOException("해당 비밀번호는 일치하지 않습니다.");
            }
            user.setIntroduce(introduce);
            userRepository.save(user);
        }else {
            throw new IllegalArgumentException("해당 사용자는 존재하지 않습니다");
        }
        return checkUserdata;
    }
}
