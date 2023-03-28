package com.project.drbot.auth.application;


import com.project.drbot.MockData;
import com.project.drbot.auth.presentation.dto.request.UserCreateDto;
import com.project.drbot.auth.presentation.dto.request.UserLoginDto;
import com.project.drbot.auth.application.AuthService;
import com.project.drbot.common.config.exception.ServiceException;
import com.project.drbot.user.domain.UserEntity;
import com.project.drbot.user.infra.UserRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
@DisplayName("회원 서비스 테스트")
public class AuthServiceTest {

    @InjectMocks
    AuthService authService;

    @Mock
    UserRepository userRepository;

    @Mock
    PasswordEncoder passwordEncoder;

    UserEntity mockUser = MockData.유저정보();

    @Nested
    @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
    class 회원_조회_테스트 {

        UserLoginDto 로그인정보() {
            String username = "test";
            String password = "1234";
            UserLoginDto userLoginDto = UserLoginDto
                    .builder()
                    .username(username)
                    .password(password)
                    .build();
            return userLoginDto;
        }

        @Test
        void 로그인_성공() {
            //given
            UserLoginDto userLoginDto = 로그인정보();

            doReturn(true).when(passwordEncoder).matches(any(String.class), any(String.class));
            doReturn(Optional.of(mockUser)).when(userRepository).findByUsername(userLoginDto.getUsername());

            //when
            UserDetails result = authService.login(userLoginDto);

            //then
            Assertions.assertEquals(userLoginDto.getUsername(), result.getUsername());
        }

        @Test
        void 로그인_실패() {
            //given
            UserLoginDto userLoginDto = 로그인정보();

            doReturn(false).when(passwordEncoder).matches(any(String.class), any(String.class));
            doReturn(Optional.of(mockUser)).when(userRepository).findByUsername(userLoginDto.getUsername());

            //when
            ServiceException e = assertThrows(ServiceException.class, () -> authService.login(userLoginDto));

            //then
            String expectedMessage = "ID나 PASSWORD가 일치하지 않습니다.";
            assertEquals(expectedMessage, e.getMessage());
        }
    }


    @Nested
    @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
    class 회원_등록_테스트 {

        UserCreateDto 회원생성요청정보() {
            UserCreateDto userCreateDto = UserCreateDto.builder().username(mockUser.getUsername())
                    .password(mockUser.getPassword())
                    .email(mockUser.getEmail())
                    .build();
            return userCreateDto;
        }

        @Test
        void 회원가입_성공() {
            //given
            String encrytedPassword = "1234!@#";
            UserCreateDto userCreateDto = 회원생성요청정보();

            doReturn(encrytedPassword).when(passwordEncoder).encode(any(String.class));
            doReturn(false).when(userRepository).existsByUsername(userCreateDto.getUsername());

            //when
            UserEntity entity = authService.signUp(userCreateDto);

            //then
            Assertions.assertEquals(entity.getUsername(), userCreateDto.getUsername());
        }

        @Test
        void 회원가입_실패() {
            //given
            String encrytedPassword = "1234!@#";
            UserCreateDto userCreateDto = 회원생성요청정보();

            doReturn(encrytedPassword).when(passwordEncoder).encode(any(String.class));
            doReturn(true).when(userRepository).existsByUsername(userCreateDto.getUsername());

            //when
            ServiceException e = assertThrows(ServiceException.class, () -> authService.signUp(userCreateDto));

            //then
            String expectedMessage = "이미 등록된 ID입니다.";
            assertEquals(expectedMessage, e.getMessage());

        }
    }

}
