package com.project.drbot.user.presentation.dto.request;

import lombok.*;

import java.time.LocalDateTime;

@Data
public class UserDto {

    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    @ToString
    public static class Request {
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

    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    public static class Response {
        private String username; // 사용자 아이디
        private String email; // 사용자 이메일
        private String imgName;
        private String role; //권한
        private LocalDateTime regDate; //가입일
    }
}


