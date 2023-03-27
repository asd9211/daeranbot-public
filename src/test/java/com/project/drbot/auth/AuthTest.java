package com.project.drbot.auth;


import com.project.drbot.auth.presentation.dto.request.UserCreateDto;
import com.project.drbot.auth.presentation.dto.request.UserLoginDto;
import com.project.drbot.auth.application.AuthService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;

@SpringBootTest
@TestMethodOrder(value = MethodOrderer.OrderAnnotation.class)
public class AuthTest {

    @Autowired
    AuthService authService;

    @Test
    @Order(1)
    @DisplayName("회원가입")
    void sign_up() {
        //given
        String username = "test9999";
        String password = "1234";
        String email = "test@naver.com";
        UserCreateDto userCreateDto = UserCreateDto.builder().username(username)
                .password(password)
                .email(email)
                .build();

        //when
        boolean result = authService.signUp(userCreateDto);

        //then
        Assertions.assertEquals(true, result);
    }

    @Test
    @Order(2)
    @DisplayName("로그인")
    void login() {
        //given
        String username = "test";
        String password = "1234";
        UserLoginDto userLoginDto = UserLoginDto
                .builder()
                .username(username)
                .password(password)
                .build();

        //when
        UserDetails result = authService.login(userLoginDto);

        //then
        Assertions.assertEquals("test", result.getUsername());

    }

}
