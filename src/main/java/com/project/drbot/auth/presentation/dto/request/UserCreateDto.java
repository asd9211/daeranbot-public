package com.project.drbot.auth.presentation.dto.request;


import com.project.drbot.user.domain.Role;
import com.project.drbot.user.domain.UserEntity;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
public class UserCreateDto {
    @NotBlank(message = "아이디를 입력하세요.")
    private String username; // 사용자 아이디
    @NotBlank(message = "비밀번호를 입력하세요.")
    private String password; // 사용자 비밀번호
    @NotBlank(message = "이메일을 입력하세요.")
    private String email; // 사용자 이메일
    private String imgName;
    private String role;

    @Builder
    public UserCreateDto(final String username, final String password, final String imgName,
                          final String role, final String email) {
        this.username = username;
        this.password = password;
        this.imgName = imgName;
        this.role = Role.USER.getValue();
        this.email = email;
    }

    public UserEntity toEntity(String encryptedPassword) {
        return UserEntity.builder()
                .username(username)
                .password(encryptedPassword)
                .imgName(imgName)
                .role(role)
                .email(email)
                .build();
    }

}
