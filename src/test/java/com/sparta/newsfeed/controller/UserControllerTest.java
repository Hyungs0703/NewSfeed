package com.sparta.newsfeed.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.newsfeed.config.WebSecurityConfig;
import com.sparta.newsfeed.dto.JoinRequestDto;
import com.sparta.newsfeed.dto.SignupRequestDto;
import com.sparta.newsfeed.dto.UpdateInfoRequestDto;
import com.sparta.newsfeed.entity.User;
import com.sparta.newsfeed.entity.UserRoleEnum;
import com.sparta.newsfeed.mvc.MockSpringSecurityFilter;
import com.sparta.newsfeed.repository.UserRepository;
import com.sparta.newsfeed.security.UserDetailsImpl;
import com.sparta.newsfeed.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.security.Principal;
import java.util.Optional;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(
        controllers = {UserController.class},
        excludeFilters = {
                @ComponentScan.Filter(
                        type = FilterType.ASSIGNABLE_TYPE,
                        classes = WebSecurityConfig.class
                )
        }
)
class UserControllerTest {
    private MockMvc mvc;

    private Principal mockPrincipal;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    UserService userService;

    @MockBean
    UserRepository userRepository;

    @BeforeEach
    public void setup() {
        mvc = MockMvcBuilders.webAppContextSetup(context)
                .apply(springSecurity(new MockSpringSecurityFilter()))
                .build();
    }

    private void mockUserSetup() {
        // Mock 테스트 유져 생성
        String username = "sollertia4351";
        String password = "robbie1234";
        String name = "테스트유저";
        String email = "sollertia4351@sparta.com";
        String introduce = "한줄소개입니다.";
        UserRoleEnum role = UserRoleEnum.USER;
        String token = "";
        User testUser = new User(username, password,name ,email ,introduce, role, token);
        UserDetailsImpl testUserDetails = new UserDetailsImpl(testUser);
        mockPrincipal = new UsernamePasswordAuthenticationToken(testUserDetails, "", testUserDetails.getAuthorities());
    }

    @Test
    @DisplayName("회원 가입 요청 처리")
    void test1() throws Exception {
        // given
        String username = "test1234";
        String password = "1234";
        String name = "테스트유저";
        String email = "test@sparta.com";
        String introduce = "한줄소개입니다";
        boolean isRole = false;
        String role = "";
        String refreshToken = "";

        SignupRequestDto signupRequestDto = new SignupRequestDto(username, password, name, email, introduce, isRole, role, refreshToken);
        userService.signup(signupRequestDto);

        String UserInfo = objectMapper.writeValueAsString(signupRequestDto);

        // when - then
        mvc.perform(post("/api/user/signup")
                        .content(UserInfo)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .principal(mockPrincipal)
                )
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("회원관련 정보받기")
    void test2() throws Exception {
        this.mockUserSetup();
        Optional<User> user =userRepository.findByUsername(mockPrincipal.getName());

        // when - then
        mvc.perform(get("/api/user/user-info")
                        .content(String.valueOf(user))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .principal(mockPrincipal)
                )
                .andExpect(status().isOk())
                .andDo(print());
    }
    @Test
    @DisplayName("회원 정보 수정하기")
    void test3() throws Exception {
        this.mockUserSetup();
        String password = "1234";
        String update = "수정합니다";
        UpdateInfoRequestDto updateInfoRequestDto = new UpdateInfoRequestDto(password, update);

        String updateInfo = objectMapper.writeValueAsString(updateInfoRequestDto);

        mvc.perform(put("/api/user/user-info")
                        .content(updateInfo)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .principal(mockPrincipal)
                )
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("회원탈퇴")
    void test4() throws Exception{
        this.mockUserSetup();
        String username = "test1234";
        String password = "1234";
        JoinRequestDto user = new JoinRequestDto(username, password);

        String userIfo = objectMapper.writeValueAsString(user);

        mvc.perform(put("/api/user/withdrawal")
                        .content(userIfo)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .principal(mockPrincipal)
                )
                .andExpect(status().isOk())
                .andDo(print());
    }
    @Test
    @DisplayName("로그아웃")
    void test5() throws Exception{
        this.mockUserSetup();
        String username = "test1234";
        String password = "1234";
        JoinRequestDto user = new JoinRequestDto(username, password);

        String logoutInfo = objectMapper.writeValueAsString(user);

        mvc.perform(delete("/api/user/logout")
                        .content(logoutInfo)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .principal(mockPrincipal)
                )
                .andExpect(status().isOk())
                .andDo(print());
    }
}
