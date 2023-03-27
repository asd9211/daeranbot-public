package com.project.drbot.auth.presentation;

import com.project.drbot.auth.presentation.dto.request.UserCreateDto;
import com.project.drbot.auth.presentation.dto.request.UserLoginDto;
import com.project.drbot.auth.application.AuthService;
import com.project.drbot.user.application.UserService;
import com.project.drbot.user.presentation.dto.response.UserInfoResponse;
import com.project.drbot.util.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthRestController {

    private final AuthService authService;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserService userService;

    /**
     * 로그인 유저의 정보를 조회합니다.
     *
     * @return 회원 상세 정보
     */
    @GetMapping("/login-info")
    public UserInfoResponse getUserInfo() {
        return userService.getLoginUserInfo();
    }

    /**
     * 로그인을 합니다.
     *
     * @param userLoginDto 로그인할 회원 정보
     * @return Access token
     */
    @PostMapping("/login")
    public String login(@RequestBody UserLoginDto userLoginDto) {
        UserDetails user = authService.login(userLoginDto);
        List<String> roles = user.getAuthorities().stream().map(row -> row.getAuthority()).collect(Collectors.toList());
        String token = jwtTokenProvider.createToken(user.getUsername(), roles);

        return token;
    }

    /**
     * 회원가입을 합니다.
     *
     * @param userCreateDto 유저 생성 정보
     * @return 성공여부
     */
    @PostMapping("/signup")
    public boolean signUp(@RequestBody UserCreateDto userCreateDto) {
        return authService.signUp(userCreateDto);
    }
}
