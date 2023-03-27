package com.project.drbot.notice.presentation.dto;

import com.project.drbot.user.domain.UserEntity;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class NoticeDto {

    @Getter
    @Setter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Request {
        private String title;
        private String content;
        private String writer;
        private LocalDateTime regDate;
        private Long viewCount;
    }

    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    public static class Response {
        private Long id;
        private String title;
        private String content;
        private String writer;
        private LocalDateTime regDate;
        private Long viewCount;
        private String imgName;

        public void setUser(UserEntity user){
            this.writer = user.getUsername();
            this.imgName = user.getImgName();
        }

//        public void setRegDate(LocalDateTime regDate) {
//            this.regDate = regDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
//        }
    }
}
