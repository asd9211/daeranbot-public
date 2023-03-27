package com.project.drbot.auth.presentation.dto.request;


import com.project.drbot.user.domain.UserEntity;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
public class UserLoginDto {
    @NotBlank(message = "아이디를 입력하세요.")
    private String username; // 사용자 아이디
    @NotBlank(message = "비밀번호를 입력하세요.")
    private String password; // 사용자 비밀번호

    @Builder
    public UserLoginDto(final String username, final String password) {
        this.username = username.toLowerCase();
        this.password = password;
    }

    public UserEntity toEntity() {
        return UserEntity.builder()
                .username(username)
                .password(password)
                .build();
    }

}
