package com.project.drbot.notice.presentation.dto.request;

import com.project.drbot.notice.domain.NoticeEntity;
import com.project.drbot.user.domain.UserEntity;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
@Builder
public class NoticeUpdateDto {
    private Long id;
    @NotBlank(message = "제목을 입력하세요.")
    private String title;
    @NotBlank(message = "내용을 입력하세요.")
    private String content;

    public NoticeEntity toEntity(UserEntity user) {
        return NoticeEntity.builder().title(title)
                .user(user)
                .content(content)
                .build();
    }
}
