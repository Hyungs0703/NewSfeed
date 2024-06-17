package com.sparta.newsfeed.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.newsfeed.config.WebSecurityConfig;
import com.sparta.newsfeed.dto.JoinRequestDto;
import com.sparta.newsfeed.dto.SignupRequestDto;
import com.sparta.newsfeed.dto.UpdateInfoRequestDto;
import com.sparta.newsfeed.entity.User;
import com.sparta.newsfeed.entity.UserRoleEnum;
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

    @BeforeEach
    public void setup() {
        mvc = MockMvcBuilders.webAppContextSetup(context)
                .apply(springSecurity(new MockSpringSecurityFilter()))
                .build();
    }

    private void mockUserSetup() {
        // Mock 테스트 유져 생성
        String username = "test12";
        String password = "1234";
        String name = "test12";
        String email = "test@sparta.com";
        String introduce = "한줄소개입니다.";
        UserRoleEnum role = UserRoleEnum.USER;
        String token = "";
        User testUser = new User(username, password,name, email,introduce, role, token);
        UserDetailsImpl testUserDetails = new UserDetailsImpl(testUser);
        mockPrincipal = new UsernamePasswordAuthenticationToken(testUserDetails, "", testUserDetails.getAuthorities());
    }

    @Test
    @DisplayName("회원 가입 요청 처리")
    void test1() throws Exception {
        SignupRequestDto signupRequestDto = new SignupRequestDto(
                "test12",
                "1234",
                "test12",
                "test@sparta.com",
                "한줄소개입니다",
                false,
                "",
                "");
        String userInfo = objectMapper.writeValueAsString(signupRequestDto);


        // when - then
        mvc.perform(post("/api/user/signup")
                        .content(userInfo)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)

                )
                .andExpect(status().isCreated())
                .andDo(print());
    }
    @Test
    @DisplayName("회원 관련 정보 받기")
    void test2() throws Exception {
        //given
        this.mockUserSetup();

        // when - then
        mvc.perform(get("/api/user/user-info")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .principal(mockPrincipal)

                )
                .andExpect(status().isFound())
                .andDo(print());
    }
    @Test
    @DisplayName("회원 소개 수정하기")
    void test3() throws Exception {
        //given
        this.mockUserSetup();
        UpdateInfoRequestDto updateIntroduce = new UpdateInfoRequestDto("1234","수정합니다.");

        String userInfo = objectMapper.writeValueAsString(updateIntroduce);

        // when - then
        mvc.perform(put("/api/user/user-info")
                        .content(userInfo)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .principal(mockPrincipal)
                )
                .andExpect(status().isUpgradeRequired())
                .andDo(print());
    }
    @Test
    @DisplayName("로그 아웃")
    void test4() throws Exception {
        //given
        this.mockUserSetup();

        // when - then
        mvc.perform(delete("/api/user/logout")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .principal(mockPrincipal)
                )
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("회원 탈퇴")
    void test5() throws Exception {
        JoinRequestDto joinRequestDto = new JoinRequestDto("test12","1234");

        String logoutInfo = objectMapper.writeValueAsString(joinRequestDto);

        // when - then
        mvc.perform(delete("/api/user/logout")
                        .content(logoutInfo)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andDo(print());
    }
}