package com.project.drbot.notice.presentation.dto.request;

import com.project.drbot.notice.domain.NoticeEntity;
import com.project.drbot.user.domain.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class NoticeCreateDto {

    @NotBlank(message = "제목을 입력하세요.")
    private String title;

    @NotBlank(message = "내용을 입력하세요.")
    private String content;
    private String writer;
    private LocalDateTime regDate;
    private Long viewCount;

    public NoticeEntity toEntity(UserEntity user) {
        return NoticeEntity.builder().title(title)
                .writer(writer)
                .user(user)
                .content(content)
                .regDate(regDate)
                .viewCount(viewCount)
                .build();
    }

}
