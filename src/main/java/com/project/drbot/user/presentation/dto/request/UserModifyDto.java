package com.project.drbot.user.presentation.dto.request;

import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class UserModifyDto {

    private String username; // 사용자 아이디
    private String password; // 사용자 비밀번호
    private String imgName;
    private String role;
    private String email; // 사용자 이메일
    private String encryptedPassword;

    public void setUsername(String username) {
        this.username = username.toLowerCase();
    }
}
